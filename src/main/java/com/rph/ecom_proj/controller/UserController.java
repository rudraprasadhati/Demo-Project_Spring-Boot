package com.rph.ecom_proj.controller;

import com.rph.ecom_proj.model.UserData;
import com.rph.ecom_proj.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserSecurityService userSecurityService;

    @PostMapping("/register")
    public UserData register(@RequestBody UserData userData){
        return userSecurityService.register(userData);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserData userData){
        return userSecurityService.verify(userData);
    }
}
