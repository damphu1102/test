package com.example.test.service;

import com.example.test.dto.AccountCreateDto;
import com.example.test.dto.AccountUpdateDto;
import com.example.test.entity.Account;
import com.example.test.entity.Role;
import com.example.test.entity.Status;
import com.example.test.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAllUser() {
        return accountRepository.findByStatusAndRole(Status.Active, Role.User);
    }

    @Override
    public Account findById(long userId) {
        Optional<Account> optional = accountRepository.findById(userId);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public Account create(AccountCreateDto dto) {
        Account account = new Account();
        BeanUtils.copyProperties(dto, account);
        // Mã hóa mật khẩu sử dụng BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        account.setPassword(encodedPassword);
        account.setRole(Role.User);
        account.setStatus(Status.Active);
        return accountRepository.save(account);
    }

    @Override
    public Account update(AccountUpdateDto dto) {
        Account account = accountRepository.findById(dto.getUserId()).orElse(null);
        if (account != null) {
            if (dto.getFullName() != null){
                account.setFullName(dto.getFullName());
            }
            if (dto.getEmail() != null){
                account.setEmail(dto.getEmail());
            }
            if (dto.getPhone() != null){
                account.setPhone(dto.getPhone());
            }
            if (dto.getPassword() != null){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedNewPassword = passwordEncoder.encode(dto.getPassword());
                account.setPassword(encodedNewPassword);
            }
            return accountRepository.save(account);
        } else {
            return null;

        }
    }

    @Override
    public void softDeleteUser(long userId) {
        Optional<Account> userOptional = accountRepository.findById(userId);
        if (userOptional.isPresent()) {
            Account account = userOptional.get();
            account.setStatus(Status.Inactive);
            accountRepository.save(account);
        } else {
            throw new ResolutionException("Account not found with id: " + userId);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> optional = accountRepository.findFirstByUserName(username);
        if (optional.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        Account account = optional.get();
        Role role = account.getRole();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(role);
        User user = new User(account.getUserName(), account.getPassword(), grantedAuthorities );
        return user;
    }
}
