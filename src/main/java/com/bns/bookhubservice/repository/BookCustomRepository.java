package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.BookEntity;
import com.bns.bookhubservice.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCustomRepository {

    // QueryDSL 도서 검색 결과 목록 조회
    public List<BookDto> searchBooks(String title);

}
