package com.sparta.sparta_reviewapi.user.repository;

import com.sparta.sparta_reviewapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
