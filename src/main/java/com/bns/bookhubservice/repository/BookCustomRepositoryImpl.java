package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.BookEntity;
import com.bns.bookhubservice.entity.MemberEntity;
import com.bns.bookhubservice.entity.QBookEntity;
import com.bns.bookhubservice.vo.response.ResponseBook;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookCustomRepositoryImpl implements BookCustomRepository {

    @PersistenceContext private EntityManager em;

    // QueryDSL 도서 검색 결과 목록 조회
    @Override
    public List<BookDto> searchBooks(String title){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QBookEntity book = new QBookEntity("tm");

        List<BookDto> result =
                queryFactory
                        .select(
                                Projections.bean(
                                        BookDto.class,
                                        book.title,
                                        book.author,
                                        book.publisher,
                                        book.thumbnailUrl))
                        .from(book)
                        .where(book.title.contains(title))
                        .groupBy(book.title)
                        .orderBy(book.rentCount.asc())
                        .fetch();
        return result;
    }

//    @Override
//    public List<MemberEntity> selectOwnerList(int id) {
//        return null;
//    }

}
