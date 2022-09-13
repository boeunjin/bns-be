package com.bns.bookhubservice.vo.request;


import lombok.Data;

@Data
public class RequestRental {
    private String memberId;
    private String bookId;
    private String channelId;
    private boolean isReturn;
}
