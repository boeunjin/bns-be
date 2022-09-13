package com.bns.bookhubservice.controller;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.dto.MemberDto;
import com.bns.bookhubservice.entity.BookEntity;
import com.bns.bookhubservice.service.BookService;
import com.bns.bookhubservice.vo.request.RequestBook;
import com.bns.bookhubservice.vo.response.ResponseBook;
import com.bns.bookhubservice.vo.response.ResponseMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("도서 정보 저장")
    public ResponseEntity<BookEntity> createBook(@RequestBody RequestBook requestParam) throws Exception {
        BookEntity bookEntity = bookService.create(requestParam);
        return ResponseEntity.status(HttpStatus.OK).body(bookEntity);
    }

    // id 조건으로 도서 정보 조회
    @GetMapping(value = "/v1/book/{id}")
    @ApiOperation("id 조건으로 도서 정보 조회")
    public ResponseEntity<ResponseBook> getBookById(@PathVariable("id") Long id) throws Exception {
        BookDto bookDto = bookService.getBookById(id);
        ResponseBook result = new ModelMapper().map(bookDto, ResponseBook.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 제목 조건으로 도서 목록 조회
    @GetMapping(value = "/v1/books/title")
    @ApiOperation("제목 조건으로 도서 목록 조회")
    public List<BookEntity> getBooks(@RequestParam(required = true) String title) throws Exception {

        List<BookEntity> list = null;

        list = bookService.getBooksByTitle(title);

        return list;
    }

    // 대여 도서 정보 업데이트
    @PatchMapping(value = "v1/book/rent/{id}")
    @ApiOperation("대여 도서 정보 업데이트")
    public ResponseEntity<ResponseBook> updateBookRent(@PathVariable("id") Long id){
        BookDto bookDto = bookService.updateBookRent(id);
        ResponseBook responseBook = new ModelMapper().map(bookDto, ResponseBook.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseBook);
    }

    // 반납 도서 정보 업데이트
    @PatchMapping(value = "v1/book/return/{id}")
    @ApiOperation("대여 도서 정보 업데이트")
    public ResponseEntity<ResponseBook> updateBookReturn(@PathVariable("id") Long id){
        BookDto bookDto = bookService.updateBookReturn(id);
        ResponseBook responseBook = new ModelMapper().map(bookDto, ResponseBook.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseBook);
    }

    // QueryDSL 도서 검색 결과 목록 조회
    @GetMapping(path="/v1/books/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("QueryDSL 도서 검색 결과 목록 조회")
    public ResponseEntity<List<BookDto>> searchBooks(@RequestParam(required = false) String title) throws Exception {

        List<BookDto> list = bookService.searchBooks(title);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }



}
