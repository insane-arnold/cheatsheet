package com.learn.authentication.service;


import com.learn.authentication.model.User;
import com.learn.authentication.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepository;
    public UserService(UserRepo userRepository, EmailService emailService) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}