package com.rph.ecom_proj.configFilter;

import com.rph.ecom_proj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    //Configured login page
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //The SecurityFilterChain with the bean annotation and the EnableWebSecurity annotation makes the Spring-Boot follow this filter chain and doesn't follow the default one.

//        http.csrf(customizer -> customizer.disable()); //Disabling csrf token
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated()); //User have to get authenticated to pass through
//        // http.formLogin(Customizer.withDefaults()); //For web form login //Disabled it by commenting because of the reason stated below(above the return statement).
//        http.httpBasic(Customizer.withDefaults()); //For tools like postman dealing with REST apis
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        //Create a stateless api. In a stateless api , the api request will contain all the necessary information to get through the server without maintaining a single session id. So it will create a session id everytime we login.
//        //For the above thing to work without maintaining a single session id , we have to disable the form login of the web.
//
//        return http.build();

        //The above thing can be also written in form of a builder pattern like the following:
//        return http
//                .csrf(customizer -> customizer.disable())
//                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .build();

        //Here we are implementing the same thing written above but with authentication removed from the register and login page.
//        return http
//                .csrf(customizer -> customizer.disable())
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("register" , "login") //States the page in which we don't require an authentication.
//                        .permitAll() //Permits all the above pages without authentication.
//                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class) //Before the authentication , we have to go through more 2 filters which are the JWT and other one is the username-password filter.
//                .build();

        //Here we are implementing Oauth2 login.
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/register" , "/login") //States the page in which we don't require an authentication.
                        .permitAll() //Permits all the above pages without authentication.
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()) //All mappings should be authenticated.
                //.httpBasic(Customizer.withDefaults()) //Disabled the basic authorization using username and password to access the api.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class) //Before the authentication , we have to go through more 2 filters which are the JWT and other one is the username-password filter.
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())) //Oauth2 authentication. //We have also used the function successHandler(new SavedRequestAwareAuthenticationSuccessHandler()) to save our session.
                .build();

    }

    //Configured authentication
    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //DaoAuthenticationProvider used to return the object of AuthenticationProvider //DaoAuthenticationProvider() is deprecated
        provider.setPasswordEncoder(passwordEncoder); //Password encryption
        provider.setUserDetailsService(userService); //setUserDetailsService() is deprecated
        return provider;
    }

    //While working with JWT we have to process a JWT token from the authentication manager.
    //So for which we have to configure the default authentication manager and create our own configuration manager.
    //This authentication manager will talk to the authentication provider.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
