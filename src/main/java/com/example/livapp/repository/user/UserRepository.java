package com.example.livapp.repository.user;

import com.example.livapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //User findUserByUsernameAndEnabled(String userName,boolean enabled);

    User findUserByUsername(String username);

    List<User> findAllByCashAmountGreaterThan(double cash);
}
