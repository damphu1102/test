package com.example.test.repository;

import com.example.test.entity.Account;
import com.example.test.entity.Role;
import com.example.test.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByStatusAndRole(Status status, Role role);

    Optional<Account> findFirstByUserName(String userName);
}
