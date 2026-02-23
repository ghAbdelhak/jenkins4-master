package com.example.livapp.security;

import com.example.livapp.model.user.Role;
import com.example.livapp.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


//Wrapper that tells Spring how to interpret the user
public class CustomUserDetails implements UserDetails {


    private final User user;



    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Returns the roles or permissions (authorities) granted to the user, e.g. ROLE_ADMIN, ROLE_USER.
    // Authorization (determining access rights).
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role:user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //Indicates whether the user account is still valid (not expired).
    // Disabling accounts automatically after a certain period if needed.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Indicates whether the user account is locked (e.g., after multiple failed login attempts).
    //Preventing login for locked accounts.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Indicates whether the user’s credentials (password) have expired.
   //Forcing users to reset passwords periodically.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Indicates whether the user is active or deactivated (e.g., by an admin).
    //Completely disabling a user’s access.
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}


