package com.example.demo_authen_java.model.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResgisterIn {
    private String userName;
    private String passWord;
    private String name;
}
