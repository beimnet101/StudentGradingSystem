package org.example.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Subject model for storing subject-specific data.
 */
@Entity
@Table(name = "subjects", catalog = "studentgradingsys")
public class Subject implements Serializable {

    private int subjectId;
    private String subjectName;
    private String subjectCode;
    private Teacher teacher; // Reference to the Teacher table

    public Subject() {
    }

    public Subject(int subjectId, String subjectName, String subjectCode, Teacher teacher) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.teacher = teacher;
    }

    @Id
    @Column(name = "subject_id", unique = true, nullable = false)
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Column(name = "subject_name", nullable = false, length = 100)
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Column(name = "subject_code", nullable = false, length = 20)
    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
