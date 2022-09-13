package com.bns.bookhubservice.vo.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RequestMember {
    private String username;
    private String email;
    private String team;
    private String location;
    private String slackId;

}
