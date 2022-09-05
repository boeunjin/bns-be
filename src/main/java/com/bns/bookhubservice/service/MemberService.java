package com.bns.bookhubservice.service;

import com.bns.bookhubservice.dto.MemberDto;
import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.repository.MemberRepository;
import com.bns.bookhubservice.vo.request.RequestMember;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service("memberService")
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 가입
    public MemberEntity create(RequestMember requestMember) throws Exception {
        MemberEntity memberEntity = new ModelMapper().map(requestMember, MemberEntity.class);
        memberEntity.setRegDate(LocalDate.now());
        memberRepository.save(memberEntity);
        return MemberEntity.builder().build();
    }

    // id 조건으로 회원 정보 조회
    public MemberDto getMemberById(Long id) throws Exception {
        MemberEntity memberEntity = memberRepository.findById(id);
//        return memberRepository.findById();
        if (memberEntity == null ){
            throw new Exception();
        }
        MemberDto memberDto = new ModelMapper().map(memberEntity, MemberDto.class);
        return memberDto;
    }

    // slack id 조건으로 회원 정보 조회
    public MemberDto getMemberBySlackId(String slackId) throws Exception {
        MemberEntity memberEntity = memberRepository.findBySlackId(slackId);
//        return memberRepository.findById();
        if (memberEntity == null ){
            throw new Exception();
        }
        MemberDto memberDto = new ModelMapper().map(memberEntity, MemberDto.class);
        return memberDto;
    }

    // 회원 이름 조건으로 회원 정보 조회
    public MemberDto getMemberByUsername(String username) throws Exception {
        MemberEntity memberEntity = memberRepository.findByUsername(username);
//        return memberRepository.findById();
        if (memberEntity == null ){
            throw new Exception();
        }
        MemberDto memberDto = new ModelMapper().map(memberEntity, MemberDto.class);
        return memberDto;
    }

    // 회원 근무지 조회
    public String getMemberLocation(String slackId) throws Exception {
        MemberEntity memberEntity = memberRepository.findBySlackId(slackId);
//        return memberRepository.findById();
        if (memberEntity == null ){
            throw new Exception();
        }
        MemberDto memberDto = new ModelMapper().map(memberEntity, MemberDto.class);
        return memberDto.getLocation();
    }

}
