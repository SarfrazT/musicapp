package com.musicappexample.musicapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicappexample.musicapp.model.User;
import com.musicappexample.musicapp.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //add createUser method
    public User createUser(User user) {
        return userRepository.save(user);
    }
}