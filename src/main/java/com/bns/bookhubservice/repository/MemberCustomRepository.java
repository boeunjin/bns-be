package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.dto.DetailDto;
import com.bns.bookhubservice.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCustomRepository {

    // 도서 소유자 목록 조회
    public List<DetailDto> searchMembers(String title);
}
