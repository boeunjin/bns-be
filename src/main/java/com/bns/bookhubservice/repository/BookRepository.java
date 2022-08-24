package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {

    // 도서 정보 저장
    BookEntity save (BookEntity bookEntity);

    // 도서 검색 - 제목
    List<BookEntity> findBooksByTitleLike(String title);

    // 도서 정보 조회
    BookEntity findById(Long id);

    // 도서 소유자 목록 조회
}
