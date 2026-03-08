package com.example.sharemate.user.repository;

import com.example.sharemate.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
