package com.bns.bookhubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RentalDto {
    private Long id;

    private String memberId;
    private Long bookId;
    private String channelId;
    private boolean isReturn;


    private LocalDate startDate;
    private LocalDate endDate;
}
