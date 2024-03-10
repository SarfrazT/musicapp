package com.musicappexample.musicapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.musicappexample.musicapp.model.Wishlist;

public interface WishlistRepository extends MongoRepository<Wishlist, Long> {
    List<Wishlist> findByUserId(Long userId);
}