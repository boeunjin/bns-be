package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    // 회원 가입
    MemberEntity save(MemberEntity memberEntity);

    // id 조건으로 회원 정보 조회
    MemberEntity findById(Long id);

    // slack id 조건으로 회원 정보 조회
    MemberEntity findBySlackId(String slackId);

    // 회원 이름 조건으로 회원 정보 조회
    MemberEntity findByUsername(String userame);


}
