package com.bns.bookhubservice.vo.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseRental {
    private Long id;

    private int memberId;
    private int bookId;
    private int ownerId;

    private LocalDate startDate;
    private LocalDate endDate;
}
