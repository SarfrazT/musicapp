package com.musicappexample.musicapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicappexample.musicapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @SuppressWarnings("unused")
    @Autowired
    private final UserService userService;

    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*I need to add createUser method using createUser method from UserService class
     * with annotation @PostMapping("/create")
     * 
     */


     
    








}