package com.bns.bookhubservice.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseMember {
    private String name;
    private String email;
    private String team;
    private String location;
    private String slackId;
}
