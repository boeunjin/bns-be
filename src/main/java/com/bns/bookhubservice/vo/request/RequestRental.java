package com.bns.bookhubservice.vo.request;

import lombok.Data;

@Data
public class RequestRental {
    private int memberId;
    private int bookId;
    private int ownerId;
}
