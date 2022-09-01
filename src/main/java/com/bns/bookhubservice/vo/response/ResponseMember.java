package com.bns.bookhubservice.vo.response;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class ResponseMember {
    private String username;
    private String email;
    private String team;
    private String location;
    private String slackId;
    private LocalDate regDate;
}
