package com.bns.bookhubservice.controller;

import com.bns.bookhubservice.dto.MemberDto;
import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.service.MemberService;
import com.bns.bookhubservice.vo.ResponseMember;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/bookhub-service/member")
public class MemberController {

    @Autowired private MemberService memberService;

    // 회원 가입
    @PostMapping(path = "/v1/members", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberEntity> createMember(@RequestBody MemberEntity requestParam) throws Exception {
        MemberEntity memberEntity = memberService.create(requestParam);
        return ResponseEntity.status(HttpStatus.OK).body(memberEntity);
    }

    // 회원 정보 조회
    @GetMapping (value = "/v1/members/{memberId}")
    public ResponseEntity<ResponseMember> getMemberByMemberId(@PathVariable("memberId") String memberId) throws Exception {
//        Optional<MemberEntity> memberEntity = memberService.findById(memberId);
//        if (memberEntity == null){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        MemberDto memberDto = new ModelMapper().map(memberEntity, MemberDto.class);
//        return ResponseEntity.status(HttpStatus.OK).body(memberDto);
        MemberDto memberDto = memberService.getMemberByMemberId(memberId);
        ResponseMember result = new ModelMapper().map(memberDto, ResponseMember.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }
}
