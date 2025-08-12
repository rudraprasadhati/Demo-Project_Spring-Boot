package com.rph.ecom_proj.service;

import com.rph.ecom_proj.model.UserData;
import com.rph.ecom_proj.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserData register(UserData userData){
        userData.setPassword(bCryptPasswordEncoder.encode(userData.getPassword()));
        userRepo.save(userData);
        return userData;
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    public String verify(UserData userData) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getUsername() , userData.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userData.getUsername());
        }
        return "Fail";
    }

}
