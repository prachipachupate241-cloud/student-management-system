package com.student.management.entity;

import jakarta.persistence.*;

@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Long studentId;

    private String studentName;

    private int javaMarks;

    private int pythonMarks;

    private int dbmsMarks;

    private int cloudMarks;

    private int total;

    private double percentage;

    private String grade;

    private String result;

    // ✅ GETTERS & SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getJavaMarks() {
        return javaMarks;
    }

    public void setJavaMarks(int javaMarks) {

        if(javaMarks < 0) {
            javaMarks = 0;
        }

        if(javaMarks > 100) {
            javaMarks = 100;
        }

        this.javaMarks = javaMarks;
    }

    public int getPythonMarks() {
        return pythonMarks;
    }

    public void setPythonMarks(int pythonMarks) {

        if(pythonMarks < 0) {
            pythonMarks = 0;
        }

        if(pythonMarks > 100) {
            pythonMarks = 100;
        }

        this.pythonMarks = pythonMarks;
    }

    public int getDbmsMarks() {
        return dbmsMarks;
    }

    public void setDbmsMarks(int dbmsMarks) {

        if(dbmsMarks < 0) {
            dbmsMarks = 0;
        }

        if(dbmsMarks > 100) {
            dbmsMarks = 100;
        }

        this.dbmsMarks = dbmsMarks;
    }

    public int getCloudMarks() {
        return cloudMarks;
    }

    public void setCloudMarks(int cloudMarks) {

        if(cloudMarks < 0) {
            cloudMarks = 0;
        }

        if(cloudMarks > 100) {
            cloudMarks = 100;
        }

        this.cloudMarks = cloudMarks;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}