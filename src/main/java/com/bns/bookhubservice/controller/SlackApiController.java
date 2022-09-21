package com.bns.bookhubservice.controller;
import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.dto.RentalDto;
import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.entity.RentalEntity;
import com.bns.bookhubservice.entity.RentalJson;
import com.bns.bookhubservice.entity.json.BlockActionsPayloads;
import com.bns.bookhubservice.entity.json.SlackJson;
import com.bns.bookhubservice.service.BookService;
import com.bns.bookhubservice.service.RentalService;
import com.bns.bookhubservice.service.slack.GroupChatService;
import com.bns.bookhubservice.service.MemberService;
import com.bns.bookhubservice.service.slack.RentalCompleteService;
import com.bns.bookhubservice.service.slack.RentalReturnCompleteService;
import com.bns.bookhubservice.service.slack.RentalSuccessService;
import com.bns.bookhubservice.util.CookieUtil;
import com.bns.bookhubservice.vo.request.RequestMember;
import com.bns.bookhubservice.vo.request.RequestRental;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.util.Map;



@RestController
@Api(value = "SlackApiController v1", tags = "Slack API")
@Slf4j
public class SlackApiController {

    @Autowired
    private final GroupChatService groupChatService;

    @Autowired
    private MemberService memberService;
    @Autowired
    private BookService bookService;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private RentalSuccessService rentalSuccessService;
    @Autowired
    private RentalCompleteService rentalCompleteService;
    @Autowired
    private RentalReturnCompleteService rentalReturnCompleteService;

    public SlackApiController(GroupChatService groupChatService) {this.groupChatService = groupChatService;}


    @Value("slack.token")
    public String token;


    @ApiOperation(value = "Slack Event Subscription")
    @PostMapping(value = "/slack/events",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> eventTest(@RequestBody Map<String, Object> data){
        try{
            SlackJson slackJson = new SlackJson(data);
            log.info("event type: "+ slackJson.getEventType());
            if (slackJson.getEventType().equals("app_mention")){
                //서비스 실행 부분
                //String responseText =service.
                log.info("envett");
            }
            if(slackJson.getEventType().equals("message")) {
                slackJson.setJson();
                log.info(slackJson.getJson().toString());
            }

            slackJson.setJson();
            return new ResponseEntity<>(slackJson.getJson(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Slack InterActivity")
    @PostMapping(value = "/slack/interactive",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public  void interactiveTest(@RequestBody MultiValueMap<String, String> data
                //, @CookieValue("ID") String Id
                                 ) throws ParseException, JsonProcessingException {
        BlockActionsPayloads blockActionsPayloads = new BlockActionsPayloads(data);

        String value =blockActionsPayloads.getButton_value();
        log.info(value);
        //String borrower = (String) CookieUtil.deserializeBasic(Id);
        //String borrower = "U037EG0M33K";
        String[] result = value.split(" ");
        String borrower = "non-user";

        if (result.length == 3){
            borrower = result[2];
        }


        log.info(borrower);


        if (value.contains("success")){

            //Database 책 대여 상태 업데이트 서비스(대여 날짜 및
            if (!blockActionsPayloads.getUser_id().equals(borrower)) //책 주인만 빌려줄 수 있게 제한
            {

                if(result[0].equals("success")){
                    Long lngId = Long.valueOf(result[1]);
                    log.info("책 주인이 빌려준다"+result[1]);
                    rentalSuccessService.successMessage(blockActionsPayloads.getChannel_id(),lngId, borrower);
                }
                else{//return_success
                    log.info("책 대여 반납 완료");
                    try {
                        Long id = Long.valueOf(result[1]);
                        RentalDto rentalDto = rentalService.getRentalById(id);
                        if (!rentalDto.isReturn()){
                            bookService.updateBookReturn(rentalDto.getBookId());
                            rentalService.updateRentalComplete(id);
                            rentalReturnCompleteService.returnCompleteMessage(rentalDto.getChannelId(), String.valueOf(rentalDto.getBookId()),LocalDate.now(ZoneId.of("Asia/Seoul")),"완료");
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }else
            {
                if (result[0].equals("rental_success")) {
                    try {
                        Long lngId = Long.valueOf(result[1]);
                        if (!rentalService.getRentalByTrippleId(borrower,lngId)){ // 버튼 한 번만 누르게
                            String channel_id = blockActionsPayloads.getChannel_id();
                            LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
                            LocalDate end = now.plusWeeks(2);
                            RequestRental rentalEntity =
                                    RequestRental.builder().memberId(borrower).bookId(Long.valueOf(result[1])).channelId(channel_id).isReturn(Boolean.FALSE).build();

                            RentalEntity resultEntity = rentalService.create(rentalEntity);

                            bookService.updateBookRent(lngId);
                            log.info("책 대여 완료");
                            //책 대여 완료 되었다는 확인 메세지 보내기
                            rentalCompleteService.completeMessage(channel_id,result[1],end);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }

        }
        else{
            if (!blockActionsPayloads.getUser_id().equals(borrower)){//책 주인만
                if (result[0].equals("extend")){
                    Long id = Long.valueOf(result[1]);
                    log.info("책 일주일 연장");
                    try {
                        RentalDto rentalDto = rentalService.getRentalById(id);
                        if (!rentalDto.isReturn()){
                            rentalService.updateRental(Long.valueOf(id));
                            rentalReturnCompleteService.returnCompleteMessage(rentalDto.getChannelId(), String.valueOf(rentalDto.getBookId()),LocalDate.now(ZoneId.of("Asia/Seoul")).plusWeeks(1),"연장");

                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
                else{
                    // 거절시 어떻게 하지?
                    log.info("책 주인이 거절한다");

                }
            }

        }

    }

    @ApiOperation(value = "책 대여하기")
    @PostMapping(value="/slack/v1/createGroup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createGroup(@RequestBody Map<String, Object> data){
        try{
            RentalJson rentalJson = new RentalJson(data);
            Long id = Long.valueOf(rentalJson.getBookId());
            BookDto bookDto = bookService.getBookById(id);

            String bookName = bookDto.getTitle();
            String bookAuthor = bookDto.getPublisher();
            String bookImageUrl = bookDto.getThumbnailUrl();
            groupChatService.groupChat(rentalJson.getOwner(),rentalJson.getMyself(),rentalJson.getBookId(), bookName, bookAuthor, bookImageUrl);
            rentalJson.setJson();
            log.info(rentalJson.getJson()+"json");
            return new ResponseEntity<>(rentalJson.getJson(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "추가 회원 정보 떨어지는 곳")
    @PostMapping(value = "/slack/v1/join",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@RequestBody Map<String, String> data, HttpServletRequest reqeuest,HttpServletResponse response,
                     @CookieValue("Email") String Email, @CookieValue("ID") String Id) {
        log.info((String) CookieUtil.deserializeBasic(Email));
        log.info((String) CookieUtil.deserializeBasic(Id));
        log.info(data.get("NAME"));
        RequestMember memberEntity =
                RequestMember.builder()
                                .username(data.get("NAME"))
                                        .email((String) CookieUtil.deserializeBasic(Email))
                                                .team(data.get("TEAM"))
                                                        .location(data.get("BUILDING"))
                                                                .slackId((String) CookieUtil.deserializeBasic(Id))
                                                                  .build();


        CookieUtil.deleteCookie(reqeuest,response,"Email");

        try {
            memberService.create(memberEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




}
