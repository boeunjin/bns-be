package com.bns.bookhubservice.vo.request;

import lombok.Data;

@Data
public class RequestBook {
    private String title;
    private String thumbnailUrl;
    private String author;
    private String publisher;

    private String ownerId;

}
