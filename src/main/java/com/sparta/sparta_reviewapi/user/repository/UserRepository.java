package com.sparta.sparta_reviewapi.user.repository;

import com.sparta.sparta_reviewapi.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    public Optional<User> findById(Long userId) {
        return null;

    }
}
