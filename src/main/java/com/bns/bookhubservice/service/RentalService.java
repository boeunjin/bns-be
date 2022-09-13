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
import java.time.ZoneId;
import java.util.List;

@Data
@Service("rentalService")
public class RentalService {

    @Autowired private RentalRepository rentalRepository;

    // 대여 정보 저장
    public RentalEntity create(RequestRental requestRental) throws Exception {
        LocalDate now =LocalDate.now(ZoneId.of("Asia/Seoul"));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RentalEntity rentalEntity = modelMapper.map(requestRental, RentalEntity.class);
        rentalEntity.setStartDate(now);
        rentalEntity.setEndDate(now.plusWeeks(2));
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

    public Boolean getRentalByTrippleId(String memberId, String bookId) throws Exception {
        RentalEntity rentalEntity = rentalRepository.findByMemberIdAndBookId(memberId,bookId);
        if(rentalEntity == null){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public List<RentalEntity> getRentalDate(LocalDate now) throws Exception{
        return rentalRepository.findByEndDateLessThanEqual(now);
    }

    // 대여 정보 업데이트
    @Transactional
    public RentalDto updateRental(Long id){
        RentalEntity rentalEntity = rentalRepository.findById(id);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        rentalEntity.setEndDate(LocalDate.now().plusWeeks(1));
        rentalRepository.save(rentalEntity);
        rentalEntity.builder().build();
        RentalDto rentalDto = mapper.map(rentalEntity, RentalDto.class);
        return rentalDto;
    }
}
