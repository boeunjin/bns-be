package com.bns.bookhubservice.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "members")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String team;
    private String location;

    @Column(nullable = false, unique = true)
    private String slackId;

    private LocalDate regDate;


}
