package com.bns.bookhubservice.service;

import com.bns.bookhubservice.dto.RentalDto;
import com.bns.bookhubservice.entity.RentalEntity;
import com.bns.bookhubservice.repository.RentalRepository;
import com.bns.bookhubservice.vo.request.RequestRental;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Data
@Service("rentalService")
public class RentalService {

    @Autowired private RentalRepository rentalRepository;

    // 대여 정보 저장
    public RentalEntity create(RequestRental requestRental) throws Exception {
        RentalEntity rentalEntity = new ModelMapper().map(requestRental, RentalEntity.class);
        rentalEntity.setStartDate(LocalDate.now());
//        rentalEntity.setEndDate();
        rentalRepository.save(rentalEntity);
        return RentalEntity.builder().build();
    }

    // 대여 정보 조회
    public RentalDto getRentalById(Long id) throws Exception {
        RentalEntity rentalEntity = rentalRepository.findById(id);
        if(rentalEntity == null){
            throw new Exception();
        }
        RentalDto rentalDto = new ModelMapper().map(rentalEntity, RentalDto.class);
        return rentalDto;
    }

    // 대여 정보 업데이트
    @Transactional
    public RentalDto updateRental(Long id){
        RentalEntity rentalEntity = rentalRepository.findById(id);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        rentalEntity.setEndDate(LocalDate.now());
        rentalRepository.save(rentalEntity);
        rentalEntity.builder().build();
        RentalDto rentalDto = mapper.map(rentalEntity, RentalDto.class);
        return rentalDto;
    }
}
