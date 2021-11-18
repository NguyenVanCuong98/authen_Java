package com.example.demo_authen_java.service.mapper;

import com.example.demo_authen_java.model.entity.Account;
import com.example.demo_authen_java.model.in.ResgisterIn;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class MapAccount {
    public static Account map(ResgisterIn resgisterIn)
    {
        Account accounts = new Account();
        accounts.setUserName(resgisterIn.getUserName());
        String hash = BCrypt.hashpw(resgisterIn.getPassWord(),BCrypt.gensalt(10));
        accounts.setPassword(hash);
        accounts.setName(resgisterIn.getName());
        return accounts;
    }
}
