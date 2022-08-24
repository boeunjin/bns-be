package com.bns.bookhubservice.service;

import com.bns.bookhubservice.dto.MemberDto;
import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("memberService")
public class MemberService {

    @Autowired private MemberRepository memberRepository;

    // 회원 가입
    public MemberEntity create(MemberEntity memberEntity) throws Exception {
        memberRepository.save(memberEntity);
        return MemberEntity.builder().build();
    }

    // 회원 정보 조회
    public MemberDto getMemberByMemberId(String memberId) throws Exception {
        MemberEntity memberEntity = memberRepository.findByMemberId(memberId);
//        return memberRepository.findById();
        if (memberEntity == null ){
            throw new Exception();
        }
        MemberDto memberDto = new ModelMapper().map(memberEntity, MemberDto.class);
        return memberDto;
    }

}
