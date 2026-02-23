package com.example.livapp.security;

import com.example.livapp.model.user.User;
import com.example.livapp.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//Fetches the user by username for authentication

    public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        @Autowired
        public CustomUserDetailsService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

     //Loads a user’s details given their username (or email, depending on your design).
    //Spring Security calls this method automatically during authentication.
    @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        //return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getRoles()));
        return new CustomUserDetails(user);

    }


    /*private List<SimpleGrantedAuthority> getAuthority(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toUnmodifiableList());
    }*/
    }