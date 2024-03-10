package com.musicappexample.musicapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.musicappexample.musicapp.model.User;


public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);
}