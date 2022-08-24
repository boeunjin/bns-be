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

    private int memberId;
    private int bookId;
    private int ownerId;

    private LocalDate startDate;
    private LocalDate endDate;
}
