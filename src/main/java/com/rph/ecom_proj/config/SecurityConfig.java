package com.rph.ecom_proj.config;

import com.rph.ecom_proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Configured login page
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //The SecurityFilterChain with the bean annotation and the EnableWebSecurity annotation makes the Spring-Boot follow this filter chain and doesn't follow the default one.

//        http.csrf(customizer -> customizer.disable()); //Disabling csrf token
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated()); //User have to get authenticated to pass through
//        // http.formLogin(Customizer.withDefaults()); //For web form login //Disabled it by commenting because of the reason stated below(above the return statement).
//        http.httpBasic(Customizer.withDefaults()); //For tools like postman dealing with REST apis
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        //Create a stateless api. In an stateless api , the api request will contain all the necessary information to get through the server without maintaining a single session id. So it will create a session id everytime we login.
//        //For the above thing to work without maintaining a single session id , we have to disable the form login of the web.
//
//        return http.build();

        //The above thing can be also written in form of a builder pattern like the following:
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

    //Configured authentication
    @Autowired
    private UserService userService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //DaoAuthenticationProvider used to return the object of AuthenticationProvider //DaoAuthenticationProvider() is deprecated
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); //Password encryption
        provider.setUserDetailsService(userService); //setUserDetailsService() is deprecated
        return provider;
    }

}
