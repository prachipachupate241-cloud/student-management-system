package com.student.management.repository;

import com.student.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 🔵 returns list to avoid "non-unique result" error
    List<User> findByUsername(String username);
}