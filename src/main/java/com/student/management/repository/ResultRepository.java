package com.student.management.repository;

import com.student.management.entity.Result;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository
        extends JpaRepository<Result, Long> {

}