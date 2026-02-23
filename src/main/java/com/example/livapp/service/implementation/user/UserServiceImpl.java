package com.example.livapp.service.implementation.user;

import com.example.livapp.model.order.Order;
import com.example.livapp.model.user.User;
import com.example.livapp.repository.user.UserRepository;
import com.example.livapp.service.abstraction.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private  BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loginUserByUsername(String username,boolean enabled){
        //return userRepository.findUserByUsernameAndEnabled(username,true);
        return userRepository.findUserByUsername(username);

    }

    @Override
    public boolean isOldPasswordCorrect(String password, String oldPassword) {
        return passwordEncoder.matches(oldPassword,password);
    }

    @Override
    public void changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getConnectedUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getUsersWithCash() {
        return userRepository.findAllByCashAmountGreaterThan(0.0);
    }

    @Override
    public void addOrUpdateUser(User user) {
        userRepository.save(user);
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long id) {
        // only ADMIN can call this
    }*/

}



