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
    private String gradeValue; // Letter grade (e.g., A, B, C)
    private String remark;
    private Date dateAssigned;
    private  double assessmentScore;
    private  double finalExamScore;
    private  double TotalScore;


    public Grade() {
    }

    public Grade(int gradeId, Subject subject, Student student, String gradeValue, String remark, Date dateAssigned,double assessmentScore,double finalExamScore) {
        this.gradeId = gradeId;
        this.subject = subject;
        this.student = student;
        this.gradeValue = gradeValue;
        this.remark = remark;
        this.dateAssigned = dateAssigned;
        this.assessmentScore=assessmentScore;
        this.finalExamScore=finalExamScore;
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
    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
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
    @Column(name = "assessment_score", nullable = false)
    public double getAssessmentScore() {
        return assessmentScore;
    }

    public void setAssessmentScore(double assessmentScore) {
        this.assessmentScore = assessmentScore;
    }

    @Column(name = "final_exam_score", nullable = false)
    public double getFinalExamScore() {
        return finalExamScore;
    }

    public void setFinalExamScore(double finalExamScore) {
        this.finalExamScore = finalExamScore;
    }


    @Column(name = "total_score", nullable = false)
    public double getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(double totalScore) {
        TotalScore = totalScore;
    }
}
