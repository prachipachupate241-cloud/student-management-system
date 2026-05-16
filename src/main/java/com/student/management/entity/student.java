package com.student.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")

public class student {

    // ✅ ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    // ✅ STUDENT NUMBER

    private String studentNo;

    // ✅ NAME

    private String name;

    // ✅ EMAIL

    private String email;

    // ✅ PHONE

    private String phone;

    // ✅ ADDRESS

    private String address;

    // ✅ COURSE

    private String course;

    // ✅ YEAR

    private String year;

    // =========================
    // GETTERS AND SETTERS
    // =========================

    // ID

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    // STUDENT NO

    public String getStudentNo() {

        return studentNo;
    }

    public void setStudentNo(String studentNo) {

        this.studentNo = studentNo;
    }

    // NAME

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    // EMAIL

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    // PHONE

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    // ADDRESS

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    // COURSE

    public String getCourse() {

        return course;
    }

    public void setCourse(String course) {

        this.course = course;
    }

    // YEAR

    public String getYear() {

        return year;
    }

    public void setYear(String year) {

        this.year = year;
    }
}