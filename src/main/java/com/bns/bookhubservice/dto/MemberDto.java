package com.bns.bookhubservice.dto;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class MemberDto {
    private String memberName;
    private String memberEmail;
    private String memberTeamName;
    private String memberLocation;
    private String memberSlackId;
    private String memberId;
}
