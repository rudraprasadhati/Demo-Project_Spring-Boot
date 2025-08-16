package com.rph.ecom_proj.controller;

import com.rph.ecom_proj.DTO.RegistrationRequest;
import com.rph.ecom_proj.model.UserData;
import com.rph.ecom_proj.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public UserData register(@RequestBody RegistrationRequest request) {
        UserData user = new UserData();
        user.setUsername(request.getUsername());
        user.setRole(List.of("USER")); //Assign default role.
        user.setPassword(request.getPassword()); //Plain password here.
        return userSecurityService.register(user); //Encrypts password internally.
    }


    @PostMapping("/login")
    public String login(@RequestBody UserData userData){
        return userSecurityService.verify(userData);
    }
}
