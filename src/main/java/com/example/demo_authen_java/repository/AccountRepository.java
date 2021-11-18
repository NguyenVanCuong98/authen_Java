package com.example.demo_authen_java.repository;

import com.example.demo_authen_java.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findAccountByUserName(String userName);
}
