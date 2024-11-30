package org.example.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Student model for storing student-specific data.
 */
@Entity
@Table(name = "students", catalog = "studentgradingsys")
public class Student implements Serializable {

    private int studentId;
    private Users user; // Reference to the Users table
    private String classLevel;

    public Student() {
    }

    public Student(int studentId, Users user, String classLevel) {
        this.studentId = studentId;
        this.user = user;
        this.classLevel = classLevel;
    }

    @Id
    @Column(name = "student_id", unique = true, nullable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Column(name = "class_level", nullable = false, length = 32)
    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }
}
