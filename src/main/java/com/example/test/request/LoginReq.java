package com.example.test.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReq {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
