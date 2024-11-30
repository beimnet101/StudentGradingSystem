package org.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Grades model for storing grade records.
 */
@Entity
@Table(name = "grades", catalog = "studentgradingsys")
public class Grade implements Serializable {

    private int gradeId;
    private Subject subject; // Reference to the Subject table
    private Student student; // Reference to the Student table
    private double gradeValue;
    private String remark;
    private Date dateAssigned;

    public Grade() {
    }

    public Grade(int gradeId, Subject subject, Student student, double gradeValue, String remark, Date dateAssigned) {
        this.gradeId = gradeId;
        this.subject = subject;
        this.student = student;
        this.gradeValue = gradeValue;
        this.remark = remark;
        this.dateAssigned = dateAssigned;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id", unique = true, nullable = false)
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Column(name = "grade_value", nullable = false)
    public double getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(double gradeValue) {
        this.gradeValue = gradeValue;
    }

    @Column(name = "remark", length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "date_assigned", nullable = false)
    public Date getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(Date dateAssigned) {
        this.dateAssigned = dateAssigned;
    }
}
