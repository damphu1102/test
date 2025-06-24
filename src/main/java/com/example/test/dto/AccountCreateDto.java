package com.example.test.dto;

import com.example.test.entity.Role;
import com.example.test.entity.Status;
import lombok.Data;



@Data
public class AccountCreateDto {
    private String userName;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private Role role;
    private Status status;

}
