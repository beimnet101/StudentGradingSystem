package org.example.Dto.req;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GiveGradeDto {
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
    private int teacherId;
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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }


}
