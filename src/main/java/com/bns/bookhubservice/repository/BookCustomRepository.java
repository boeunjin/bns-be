package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.BookEntity;
import com.bns.bookhubservice.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCustomRepository {

    public List<BookDto> selectBookList(String title);

    public List<MemberEntity> selectOwnerList(int id);

}
