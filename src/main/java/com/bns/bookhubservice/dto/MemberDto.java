package com.bns.bookhubservice.dto;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class MemberDto {
    private String username;
    private String email;
    private String team;
    private String location;
    private String slackId;
//    private LocalDate regDate;
}
