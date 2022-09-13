package com.bns.bookhubservice.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 300)
    private String thumbnailUrl;
    private String author;
    private String publisher;

    private String ownerId;

    private boolean isRented;
    private int rentCount;

    private LocalDate regDate;
}