package com.example.livapp.service.abstraction.user;

import com.example.livapp.model.user.User;

import java.util.List;

public interface UserService {

    User loginUserByUsername(String username, boolean enabled);

    boolean isOldPasswordCorrect(String password, String oldPassword);

    void changePassword(User user,String newPassword);

    List<User> getUsers();

    User getConnectedUser();

    User getUserById(long id);

    List<User> getUsersWithCash();

    void addOrUpdateUser(User user);
}
