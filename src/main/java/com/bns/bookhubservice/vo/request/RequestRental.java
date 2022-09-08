package com.bns.bookhubservice.vo.request;

import lombok.Data;

@Data
public class RequestRental {
    private String proposerId;
    private int bookId;
    private String ownerId;
}
