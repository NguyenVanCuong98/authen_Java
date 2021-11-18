package com.example.demo_authen_java.controller;

import com.example.demo_authen_java.model.bo.SystemResponse;
import com.example.demo_authen_java.model.in.LoginIn;
import com.example.demo_authen_java.model.in.ResgisterIn;
import com.example.demo_authen_java.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping(value = "/resgister")
    public ResponseEntity<?> resgister(@RequestBody ResgisterIn resgisterIn){
        return new ResponseEntity<>(accountService.resgister(resgisterIn), HttpStatus.OK);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<SystemResponse<Object>> login(@RequestBody LoginIn loginIn){
        return accountService.login(loginIn);
    }
    @GetMapping(value = "/test")
    public ResponseEntity<?> test(){
        return new ResponseEntity<>("ok test thanh cong", HttpStatus.OK);
    }
}
