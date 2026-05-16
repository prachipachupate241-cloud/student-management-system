package com.student.management.repository;

import com.student.management.entity.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository
        extends JpaRepository<student, Long> {

    // 🔍 SEARCH

    List<student>
    findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrStudentNoContainingIgnoreCase(

            String name,
            String email,
            String studentNo
    );

    // 📊 COURSE COUNT

    List<student> findByCourse(String course);

    // ✅ FILTER COURSE + YEAR

    List<student> findByCourseAndYear(

            String course,

            String year
    );
}
