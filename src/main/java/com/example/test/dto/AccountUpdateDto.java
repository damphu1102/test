package com.example.test.dto;

import lombok.Data;

@Data
public class AccountUpdateDto {
    private long userId;
    private String userName;
    private String fullName;
    private String email;
    private String password;
    private String phone;
}
