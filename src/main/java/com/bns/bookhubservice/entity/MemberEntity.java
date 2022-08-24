package com.bns.bookhubservice.entity;


import lombok.*;

import javax.persistence.*;


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
    private String member_id;

    private String memberName;
    private String memberEmail;
    private String memberTeamName;
    private String memberLocation;

    @Column(nullable = false, unique = true)
    private String memberSlackId;


}
