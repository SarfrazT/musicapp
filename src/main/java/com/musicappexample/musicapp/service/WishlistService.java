package com.musicappexample.musicapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicappexample.musicapp.model.Wishlist;
import com.musicappexample.musicapp.repository.WishlistRepository;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> findByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    // other service methods
}