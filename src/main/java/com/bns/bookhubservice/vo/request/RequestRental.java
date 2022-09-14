package com.bns.bookhubservice.vo.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestRental {
    private String memberId;
    private Long bookId;
    private String channelId;
    private boolean isReturn;
}
