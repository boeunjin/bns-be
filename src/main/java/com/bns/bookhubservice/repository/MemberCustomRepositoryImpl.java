package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.dto.DetailDto;
import com.bns.bookhubservice.dto.MemberDto;
import com.bns.bookhubservice.entity.QBookEntity;
import com.bns.bookhubservice.entity.QMemberEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    @PersistenceContext private EntityManager em;

    // 도서 소유자 목록 조회
    @Override
    public List<DetailDto> searchMembers(String title) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMemberEntity member = new QMemberEntity("tm");
        QBookEntity book = new QBookEntity("tb");
        List<DetailDto> result =
                queryFactory
                        .select(
                                Projections.bean(
                                        DetailDto.class,
                                        member.username,
                                        member.location,
                                        member.team,
                                        member.email,
                                        book.isRented,
                                        book.rentCount))
                        .from(member)
                        .join(book)
                        .on(member.slackId.eq(book.ownerId))
                        .where(book.title.eq(title))
                        .orderBy(member.id.asc())
                        .fetch();
        return result;

    }
}
