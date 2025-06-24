package com.example.test.controller;

import com.example.test.dto.LoginDto;
import com.example.test.entity.Account;
import com.example.test.repository.AccountRepository;
import com.example.test.request.LoginReq;
import com.example.test.utils.JWTTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Validated
public class AuthController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JWTTokenUtils jwtTokenUtils;

    @PostMapping("/login")
    public LoginDto loginJWT(@RequestBody @Valid LoginReq request) {
//        Check tồn tại của username
        Optional<Account> optional = accountRepository.findFirstByUserName(request.getUserName());
        if (optional.isEmpty()) {
            return null; // thay bằng bắn ra lỗi
        }
        Account account = optional.get();
//        So sánh password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //mã hóa password
        boolean isPassWord = encoder.matches(request.getPassword(), account.getPassword());
        if (!isPassWord) {
            return null; // thay bằng bắn ra lỗi
        }
//        Tạo ra đối tượng loginDto (tạo ra token)
        LoginDto loginDto = new LoginDto();
        BeanUtils.copyProperties(account, loginDto);
//        Tạo ra chuỗi token
        String token = jwtTokenUtils.createAccessToken(loginDto);
        loginDto.setToken(token);
        return loginDto;
    }
}
