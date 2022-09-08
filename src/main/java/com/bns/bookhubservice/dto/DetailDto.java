package com.bns.bookhubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DetailDto {
    private boolean isRented;
    private String username;
    private String email;
    private String team;
    private String location;
    private int rent_count;
}
