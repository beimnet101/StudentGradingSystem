package org.example.Dto.res;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GradeReportDto {
    private int studentId;        // Student ID
    private String subjectCode;

    private double assessmentGrade;
    private double finalExamGrade;

    public double getAssessmentGrade() {
        return assessmentGrade;
    }

    public void setAssessmentGrade(double assessmentGrade) {
        this.assessmentGrade = assessmentGrade;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // Subject code
    private String remark;


    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

}
