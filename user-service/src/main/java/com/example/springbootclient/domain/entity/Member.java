package com.example.springbootclient.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String name;

    private String email;

    private String phoneNumber;

    @Builder
    public Member(
            String memberId,
            String password,
            Authority authority,
            String name,
            String email,
            String phoneNumber
    ) {
        this.memberId = memberId;
        this.password = password;
        this.authority = authority;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
