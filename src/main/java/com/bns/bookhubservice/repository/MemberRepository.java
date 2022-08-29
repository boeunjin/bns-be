package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    // 회원 가입
    MemberEntity save(MemberEntity memberEntity);

    //회원 정보 조회
//    MemberEntity findByMemberSlackId(String slackId);

//    MemberEntity findByMemberName(String memberName);

    MemberEntity findById(Long id);
}
