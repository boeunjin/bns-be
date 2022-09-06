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
@Table(name = "rentals")
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String proposerId;
    private int bookId;
    private String ownerId;

    private LocalDate startDate;
    private LocalDate endDate;
}
