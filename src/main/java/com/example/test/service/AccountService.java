package com.example.test.service;

import com.example.test.dto.AccountCreateDto;
import com.example.test.dto.AccountUpdateDto;
import com.example.test.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {
    List<Account> getAllUser();

    Account findById(long userId);

    Account create (AccountCreateDto dto);

    Account update (AccountUpdateDto dto);

    void softDeleteUser(long userId);
}
