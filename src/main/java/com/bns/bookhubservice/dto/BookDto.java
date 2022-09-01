package com.bns.bookhubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class BookDto {
//    private Long id;
    private String title;
    private String thumbnailUrl;
    private String author;
    private String publisher;

    private int ownerId;

//    private boolean isRented;
//    private int rentCount;

//    private LocalDate regDate;

}
