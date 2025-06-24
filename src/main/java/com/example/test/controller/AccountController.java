package com.example.test.controller;

import com.example.test.dto.AccountCreateDto;
import com.example.test.dto.AccountUpdateDto;
import com.example.test.entity.Account;
import com.example.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")

public class AccountController {
    @Autowired
    private AccountService accountService;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    public List<Account> getAllUser(){
        return accountService.getAllUser();
    }

    @PreAuthorize("hasAuthority('Admin') or hasAuthority('User')" )
    @GetMapping("/{userId}")
        public Account findById (@PathVariable long userId){
        return accountService.findById(userId);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/delete/{userId}")
    public String delete (@PathVariable long userId){
        accountService.softDeleteUser(userId);
        return "Xóa thành công";
    }

    @PostMapping ("/create")
    public Account create(@RequestBody AccountCreateDto dto){
        return accountService.create(dto);
    }

    @PreAuthorize("hasAuthority('Admin') or hasAuthority('User')" )
    @PutMapping("/update/{userId}")
    public Account update (@RequestBody AccountUpdateDto dto){
        return accountService.update(dto);
    }

}
