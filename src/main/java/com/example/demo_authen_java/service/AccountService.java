package com.example.demo_authen_java.service;

import com.example.demo_authen_java.model.bo.SystemResponse;
import com.example.demo_authen_java.model.in.LoginIn;
import com.example.demo_authen_java.model.in.ResgisterIn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Map;
@Service
public interface AccountService extends UserDetailsService {
    Map<String,Object> resgister(ResgisterIn resgisterIn);
    ResponseEntity<SystemResponse<Object>> login(LoginIn loginIn);
    UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException;
}
