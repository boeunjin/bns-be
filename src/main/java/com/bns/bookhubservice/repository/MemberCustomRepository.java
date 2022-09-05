package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberCustomRepository {

    public List<MemberDto> selectMemberList();
}
