package com.bns.bookhubservice.controller;
import com.bns.bookhubservice.entity.RentalJson;
import com.bns.bookhubservice.entity.json.BlockActionsPayloads;
import com.bns.bookhubservice.entity.json.SlackJson;
import com.bns.bookhubservice.service.GroupChatService;
import com.bns.bookhubservice.util.CookieUtil;
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
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Map;

import static com.bns.bookhubservice.config.oauth.CustomOAuth2UserService.Email;


@RestController
@Api(value = "SlackApiController v1", tags = "Slack API")
@Slf4j
public class SlackApiController {

    @Autowired
    private final GroupChatService groupChatService;

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
    public  void interactiveTest(@RequestBody MultiValueMap<String, String> data) throws ParseException, JsonProcessingException {
        BlockActionsPayloads blockActionsPayloads = new BlockActionsPayloads(data);
        if (blockActionsPayloads.getButton_value().equals("success")){
            //Database 책 대여 상태 업데이트 서비스(대여 날짜 및
            log.info("sss success");
            blockActionsPayloads.setJson();
            log.info(blockActionsPayloads.getJson().toString());
        }
        else{
            // 거절시 어떻게 하지?
            log.info("sss fail");

        }

    }

    @ApiOperation(value = "책 대여하기")
    @PostMapping(value="/slack/v1/createGroup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createGroup(@RequestBody Map<String, Object> data){
        try{
            RentalJson rentalJson = new RentalJson(data);
            groupChatService.groupChat(rentalJson.getOwner(),rentalJson.getMyself(), rentalJson.getBookName(), rentalJson.getAuthor(), rentalJson.getImage());
            rentalJson.setJson();
            return new ResponseEntity<>(rentalJson.getJson(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "updataRentalBook")
    @GetMapping(value = "/slack/v1/updateRentalBook")
    public void updateRentalBook() {
        System.out.println("update");
    }


    @ApiOperation(value = "추가 회원 정보 떨어지는 곳")
    @PostMapping(value = "/slack/v1/join",produces = MediaType.APPLICATION_JSON_VALUE)
    public void join(@RequestBody Map<String, String> data, HttpServletRequest request) {
        log.info(data.get("NAME"));




    }


    @GetMapping("/slack/v1/getCookie1")
    public void getCookie1(@CookieValue String useremail, @CookieValue("useremail") String umail) {
        log.info("실행");
        log.info(umail);
    }








}
