package com.rph.ecom_proj.configFilter;

import com.rph.ecom_proj.service.JWTService;
import com.rph.ecom_proj.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); //We will only deal with "Authorization" header.
        String token = null;
        String username = null;

        //example of a actual Bearer token which is passed behind the scenes:
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYXJpIiwiaWF0IjoxNzU1MDAyMjQzLCJleHAiOjE3NTUwMDIzNTF9.P2cdkr0ACJHKisxQPzIak01EQXPAdA3bp3gcNdbxu9s
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7); //The bearer token's token start at the index number 7. You can refer to the example given above.
            username = jwtService.extractUserName(token); //The username is hidden inside the bearer token which we have to extract from it to verify.
        }

        //If we get a username to verify and that username is not authenticated yet , then we will proceed.
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = applicationContext.getBean(UserService.class).loadUserByUsername(username);
            if(jwtService.validateToken(token , userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request , response);

    }

}