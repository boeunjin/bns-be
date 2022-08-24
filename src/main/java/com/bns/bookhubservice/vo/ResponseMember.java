package com.bns.bookhubservice.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseMember {
    private String memberName;
    private String memberEmail;
    private String memberTeamName;
    private String memberLocation;
    private String memberSlackId;
}
