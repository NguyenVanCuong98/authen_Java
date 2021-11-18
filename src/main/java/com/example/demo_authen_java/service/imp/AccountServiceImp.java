package com.example.demo_authen_java.service.imp;

import com.example.demo_authen_java.jwt.JwtUtility;
import com.example.demo_authen_java.model.bo.Response;
import com.example.demo_authen_java.model.bo.SystemResponse;
import com.example.demo_authen_java.model.entity.Account;
import com.example.demo_authen_java.model.entity.Role;
import com.example.demo_authen_java.model.in.LoginIn;
import com.example.demo_authen_java.model.in.ResgisterIn;
import com.example.demo_authen_java.repository.AccountRepository;
import com.example.demo_authen_java.repository.RoleRepository;
import com.example.demo_authen_java.service.AccountService;
import com.example.demo_authen_java.service.mapper.MapAccount;
import com.example.demo_authen_java.utils.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class AccountServiceImp implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtility jwtUtility;





    @Override
    public Map<String, Object> resgister(ResgisterIn resgisterIn) {
        Account accounts = MapAccount.map(resgisterIn);
        Role role = roleRepository.findByName("Admin");
        accounts.setRoles(Collections.singleton(role));
        accountRepository.save(accounts);
        Map<String,Object> result = new HashMap<>();
        String message = "ok";
        result.put("result",message);
        return result;
    }

    @Override
    public ResponseEntity<SystemResponse<Object>> login(LoginIn loginIn) {
        Account account = accountRepository.findAccountByUserName(loginIn.getUserName());
        if(account == null)
        {
            return Response.badRequest(StringResponse.Account_Not_Found);
        }
        if(BCrypt.checkpw(loginIn.getPassWord(),account.getPassword()) == false) {
            return Response.badRequest(StringResponse.Pass_Wrong);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginIn.getUserName(),loginIn.getPassWord()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtility.generateJwtToken(loginIn.getUserName());
        return Response.ok(jwt);
    }

    @Override
    public UserDetails loadUserByUsername(String var1) throws UsernameNotFoundException {
        Account user = accountRepository.findAccountByUserName(var1);
        if(user == null)
        {
            throw new UsernameNotFoundException("User " + var1 + " was not found in the database");
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        List<Role> userRole = (List<Role>) user.getRoles();
        for (int i = 0;i<userRole.size();i++)
        {
            System.out.printf(""+userRole.get(i).getName());
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+userRole.get(i).getName());
            grantedAuthorityList.add(grantedAuthority);

        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),grantedAuthorityList);
    }
}
