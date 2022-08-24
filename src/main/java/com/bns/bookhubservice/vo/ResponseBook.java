package com.bns.bookhubservice.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseBook {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String author;
    private String publisher;

    private int ownerId;

    private boolean isRented;
    private int rentCount;

    private LocalDate regDate;

}
