package com.student.management.service;

import com.student.management.entity.student;
import com.student.management.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    // 📋 GET ALL STUDENTS

    public List<student> getAllStudents() {

        return repository.findAll();
    }

    // 💾 SAVE STUDENT

    public void saveStudent(student student) {

        repository.save(student);
    }

    // 🔍 GET STUDENT BY ID

    public student getById(Long id) {

        return repository.findById(id)
                .orElse(null);
    }

    // ❌ DELETE STUDENT

    public void deleteById(Long id) {

        repository.deleteById(id);
    }

    // 🔍 SEARCH STUDENT

    public List<student> searchStudents(
            String keyword
    ) {

        return repository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrStudentNoContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword
                );
    }

    // 📊 TOTAL STUDENTS

    public long totalStudents() {

        return repository.count();
    }

    // 📊 IT COUNT

    public long totalITStudents() {

        return repository
                .findByCourse("IT")
                .size();
    }

    // 📊 BCA COUNT

    public long totalBCAStudents() {

        return repository
                .findByCourse("BCA")
                .size();
    }

    // 📊 BCOM COUNT

    public long totalBCOMStudents() {

        return repository
                .findByCourse("BCOM")
                .size();
    }

    // ✅ FILTER COURSE + YEAR

    public List<student> filterStudents(

            String course,

            String year
    ) {

        return repository
                .findByCourseAndYear(
                        course,
                        year
                );
    }

    // ✅ AUTO GENERATE STUDENT NUMBER

    public String generateStudentNo(String course) {

        long count = repository.findAll()

                .stream()

                .filter(s ->
                        s.getCourse()
                                .equalsIgnoreCase(course)
                )

                .count();

        return course.toUpperCase() + "-" + (count + 1);
    }

}

