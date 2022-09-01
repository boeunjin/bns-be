package com.bns.bookhubservice.controller;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.BookEntity;
import com.bns.bookhubservice.service.BookService;
import com.bns.bookhubservice.vo.request.RequestBook;
import com.bns.bookhubservice.vo.response.ResponseBook;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "BookController v1", tags = "도서 API")
@RequestMapping("/bookhub-service/book")
public class BookController {

    @Autowired
    private final BookService bookService;

    // 도서 정보 저장
    @PostMapping(path = "/v1/book", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookEntity> createBook(@RequestBody RequestBook requestParam) throws Exception {
        BookEntity bookEntity = bookService.create(requestParam);
        return ResponseEntity.status(HttpStatus.OK).body(bookEntity);
    }

    // 도서 검색 - 제목
    @GetMapping(value = "/v1/books/search")
    public List<BookEntity> getBooks(@RequestParam(required = true) String title) throws Exception {

        List<BookEntity> list = null;

        list = bookService.getBookByTitle(title);

        return list;
    }

    // 도서 정보 조회
    @GetMapping(value = "/v1/book/{id}")
    public ResponseEntity<ResponseBook> getBookById(@PathVariable("id") Long id) throws Exception {
        BookDto bookDto = bookService.getBookById(id);
        ResponseBook result = new ModelMapper().map(bookDto, ResponseBook.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 도서 정보 업데이트 - 대여
    @PatchMapping(value = "v1/book/rent/{id}")
    public ResponseEntity<ResponseBook> updateBookRent(@PathVariable("id") Long id){
        BookDto bookDto = bookService.updateBookRent(id);
        ResponseBook responseBook = new ModelMapper().map(bookDto, ResponseBook.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseBook);
    }

    // 도서 정보 업데이트 - 반납
    @PatchMapping(value = "v1/book/return/{id}")
    public ResponseEntity<ResponseBook> updateBookReturn(@PathVariable("id") Long id){
        BookDto bookDto = bookService.updateBookReturn(id);
        ResponseBook responseBook = new ModelMapper().map(bookDto, ResponseBook.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseBook);
    }

    // 도서 소유자 목록 조회







}
