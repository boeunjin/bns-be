package com.bns.bookhubservice.service;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.BookEntity;
import com.bns.bookhubservice.repository.BookRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Data
@Service("bookService")
public class BookService {
    @Autowired private BookRepository bookRepository;

    // 도서 정보 저장
    public BookEntity create(BookEntity bookEntity) throws Exception {
        bookRepository.save(bookEntity);
        return BookEntity.builder().build();
    }

    // 도서 검색 - 제목
    public List<BookEntity> getBookByTitle(String title) throws Exception {
        return bookRepository.findBooksByTitleLike("%"+title+"%");
    }

    // 도서 정보 조회
    public BookDto getBookById(Long id) throws Exception {
        BookEntity bookEntity = bookRepository.findById(id);
        if(bookEntity == null){
            throw new Exception();
        }
        BookDto bookDto = new ModelMapper().map(bookEntity, BookDto.class);
        return bookDto;
    }

    // 도서 정보 업데이트 - 대여
    @Transactional
    public BookDto updateBookRent(Long id) {
        BookEntity bookEntity = bookRepository.findById(id);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        bookEntity.setRented(true);
        int cnt = bookEntity.getRentCount();
        bookEntity.setRentCount(cnt+1);
        bookRepository.save(bookEntity);
        bookEntity.builder().build();
        BookDto bookDto = mapper.map(bookEntity, BookDto.class);
        return bookDto;
    }

    // 도서 정보 업데이트 - 반납
    @Transactional
    public BookDto updateBookReturn(Long id){
        BookEntity bookEntity = bookRepository.findById(id);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        bookEntity.setRented(false);
        bookRepository.save(bookEntity);
        BookDto bookDto = mapper.map(bookEntity, BookDto.class);
        return bookDto;
    }

    // 도서 소유자 목록 조회

}
