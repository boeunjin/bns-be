package com.bns.bookhubservice.vo.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseBook {
    private String title;
    private String thumbnailUrl;
    private String author;
    private String publisher;

    private String ownerId;

    private boolean isRented;
    private int rentCount;

    private LocalDate regDate;

}
