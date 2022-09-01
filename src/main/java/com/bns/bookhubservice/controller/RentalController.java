package com.bns.bookhubservice.controller;

import com.bns.bookhubservice.dto.RentalDto;
import com.bns.bookhubservice.entity.RentalEntity;
import com.bns.bookhubservice.service.RentalService;
import com.bns.bookhubservice.vo.request.RequestRental;
import com.bns.bookhubservice.vo.response.ResponseRental;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "RentalController v1", tags = "대여 API")
@RequestMapping("/bookhub-service/rental")
public class RentalController {

    @Autowired
    private final RentalService rentalService;

    // 대여 정보 저장
    @PostMapping(path = "/v1/rental", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalEntity> createBook(@RequestBody RequestRental requestParam) throws Exception {
        RentalEntity rentalEntity = rentalService.create(requestParam);
        return ResponseEntity.status(HttpStatus.OK).body(rentalEntity);
    }

    // 대여 정보 조회
    @GetMapping(value = "/v1/rental/{id}")
    public ResponseEntity<ResponseRental> getRentalById(@PathVariable("id") Long id) throws Exception {
        RentalDto rentalDto = rentalService.getRentalById(id);
        ResponseRental result = new ModelMapper().map(rentalDto, ResponseRental.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 대여 정보 업데이트
    @PatchMapping(value = "v1/rental/return/{id}")
    public ResponseEntity<ResponseRental> updateRental(@PathVariable("id") Long id){
        RentalDto rentalDto = rentalService.updateRental(id);
        ResponseRental responseRental = new ModelMapper().map(rentalDto, ResponseRental.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseRental);
    }
}
