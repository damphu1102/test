package com.example.test.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table (name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "USER_ID")
    private long userId;

    @Column(name = "USERNAME", length = 20, nullable = false, unique = true)
    private String userName;

    @Column(name = "FULLNAME", length = 100, nullable = false)
    private String fullName;

    @Column(name = "EMAIL", length = 255, nullable = false, unique = true)
    private String emailAccount;

    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String passWord;

    @Column(name = "PHONE", length = 10, nullable = false)
    private String phoneNumber;

    @Column(name = "DATE_BIRD")
    private LocalDate    dateBird;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
