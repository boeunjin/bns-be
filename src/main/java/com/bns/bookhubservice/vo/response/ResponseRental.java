package com.bns.bookhubservice.vo.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseRental {
    private Long id;

    private String memberId;
    private Long bookId;
    private String channelId;
    private boolean isReturn;

    private LocalDate startDate;
    private LocalDate endDate;
}
