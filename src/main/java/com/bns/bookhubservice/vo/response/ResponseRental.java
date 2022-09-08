package com.bns.bookhubservice.vo.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseRental {
    private Long id;

    private String proposerId;
    private int bookId;
    private String ownerId;

    private LocalDate startDate;
    private LocalDate endDate;
}
