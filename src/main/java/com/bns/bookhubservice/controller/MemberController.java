package com.bns.bookhubservice.controller;

import com.bns.bookhubservice.dto.DetailDto;
import com.bns.bookhubservice.dto.MemberDto;
import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.service.MemberService;
import com.bns.bookhubservice.vo.request.RequestMember;
import com.bns.bookhubservice.vo.response.ResponseMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "MemberController v1", tags = "회원정보 API")
@Slf4j
@RequestMapping("/bookhub-service/member")
public class MemberController {

    @Autowired private MemberService memberService;

    // 회원 가입
    @PostMapping(path = "/v1/member", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("회원가입")
    public ResponseEntity<MemberEntity> createMember(@RequestBody RequestMember requestParam) throws Exception {
        MemberEntity memberEntity = memberService.create(requestParam);
        return ResponseEntity.status(HttpStatus.OK).body(memberEntity);
    }
    // OIDC 회원 가입
    @PostMapping(path = "/v1/oidcMember", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("oidc 회원가입")
    public ResponseEntity<MemberEntity> createOIDCMember(@RequestBody RequestMember requestParam) throws Exception {
        MemberEntity memberEntity = memberService.create(requestParam);
        return ResponseEntity.status(HttpStatus.OK).body(memberEntity);
    }

    // id 조건으로 회원 정보 조회
    @GetMapping (value = "/v1/member/{id}")
    @ApiOperation("id 조건으로 회원 정보 조회")
    public ResponseEntity<ResponseMember> getMemberById(@PathVariable("Id") Long id) throws Exception {

        MemberDto memberDto = memberService.getMemberById(id);
        ResponseMember result = new ModelMapper().map(memberDto, ResponseMember.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // slack id 조건으로 회원 정보 조회
    @GetMapping (value = "/v1/member/slackId")
    @ApiOperation("slack id 조건으로 회원 정보 조회")
    public ResponseEntity<ResponseMember> getMemberBySlackId(@RequestParam(required = false)String slackId) throws Exception {

        MemberDto memberDto = memberService.getMemberBySlackId(slackId);
        ResponseMember result = new ModelMapper().map(memberDto, ResponseMember.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 회원 이름 조건으로 회원 정보 조회
    @GetMapping (value = "/v1/members/{username}")
    @ApiOperation("회원정보 이름으로 조회")
    public ResponseEntity<ResponseMember> getMemberByUsername(@RequestParam(required = false) String username) throws Exception {

        MemberDto memberDto = memberService.getMemberByUsername(username);
        ResponseMember result = new ModelMapper().map(memberDto, ResponseMember.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // QueryDSL 소유자 목록 조회
    @GetMapping(path="/v1/members/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("QueryDSL 소유자 목록 조회")
    public ResponseEntity<List<DetailDto>> searchMembers(@RequestParam(required = false) String title) throws Exception {
        List<DetailDto> list = memberService.searchMembers(title);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
