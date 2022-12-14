package com.bns.bookhubservice.service;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.BookEntity;
import com.bns.bookhubservice.repository.BookCustomRepository;
import com.bns.bookhubservice.repository.BookRepository;
import com.bns.bookhubservice.vo.request.RequestBook;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Data
@Service("bookService")
public class BookService {
    @Autowired private BookRepository bookRepository;
    @Autowired private BookCustomRepository bookCustomRepository;

    // 도서 정보 저장
    public BookEntity create(RequestBook requestBook) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BookEntity bookEntity = modelMapper.map(requestBook, BookEntity.class);
        bookEntity.setRented(false);
        bookEntity.setRentCount(0);
        bookEntity.setRegDate(LocalDate.now(ZoneId.of("Asia/Seoul")));
        bookRepository.save(bookEntity);
        return BookEntity.builder().build();
    }

    // id 조건으로 도서 정보 조회
    public BookDto getBookById(Long id) throws Exception {
        BookEntity bookEntity = bookRepository.findById(id);
        if(bookEntity == null){
            throw new Exception();
        }
        BookDto bookDto = new ModelMapper().map(bookEntity, BookDto.class);
        return bookDto;
    }

    // 제목 조건으로 도서 목록 조회
    public List<BookEntity> getBooksByTitle(String title) throws Exception {
        return bookRepository.findBooksByTitleLike("%"+title+"%");
    }

    // 대여 도서 정보 업데이트
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

    // 반납 도서 정보 업데이트
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

    // QueryDSL 도서 검색 결과 목록 조회
    public List<BookDto> searchBooks(String title) throws Exception {
        return bookCustomRepository.searchBooks(title);
    }

    public List<BookDto> searchBooksAsc(String title) throws Exception {
        return bookCustomRepository.searchBooksAsc(title);
    }

    public List<BookDto> searchBooksDesc(String title) throws Exception {
        return bookCustomRepository.searchBooksDesc(title);
    }

    public List<BookDto> searchBooksReg(String title) throws Exception {
        return bookCustomRepository.searchBooksReg(title);
    }




}
