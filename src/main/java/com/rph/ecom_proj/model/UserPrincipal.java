package com.rph.ecom_proj.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

//This class is used for the implementation of the interface UserDetails
//This class is used in UserService

public class UserPrincipal implements UserDetails {

    private UserData userData;

    public UserPrincipal(UserData userData) {
        this.userData = userData;
    }

    //Copied from online. Maps my role from userdata to internal authorities.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(userData.getRole())
                .orElse(Collections.emptyList())
                .stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {

        return userData.getPassword();
    }

    @Override
    public String getUsername() {

        return userData.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;	}


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
