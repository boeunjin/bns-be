package com.bns.bookhubservice.repository;

import com.bns.bookhubservice.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, String> {

    // 대여 정보 저장
    RentalEntity save (RentalEntity rentalEntity);

    // 대여 정보 조회
    RentalEntity findById(Long id);

}
