package org.example.service;

import org.example.Dto.res.GradeReportDto;
import org.example.dao.GradeDao;
import org.example.dao.SubjectDao;
import org.example.model.Grade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
@Stateless
public class GpaService {

    @EJB
    GradeDao gradeDao;
    @EJB
    SubjectDao subjectDao;

    // Convert letter grade to grade points
    private double convertToGradePoints(String grade) {
        switch (grade) {
            case "A+":
                return 4.33;  // A+ = 4.33 GPA points
            case "A":
                return 4.00;  // A = 4.00 GPA points
            case "A-":
                return 3.67;  // A- = 3.67 GPA points
            case "B+":
                return 3.33;  // B+ = 3.33 GPA points
            case "B":
                return 3.00;  // B = 3.00 GPA points
            case "B-":
                return 2.67;  // B- = 2.67 GPA points
            case "C+":
                return 2.33;  // C+ = 2.33 GPA points
            case "C":
                return 2.00;  // C = 2.00 GPA points
            case "C-":
                return 1.67;  // C- = 1.67 GPA points
            case "D":
                return 1.00;  // D = 1.00 GPA points
            case "F":
                return 0.00;  // F = 0.00 GPA points
            default:
                throw new IllegalArgumentException("Invalid grade: " + grade);  // Throw error for invalid grades
        }
    }


    // Calculate GPA for a student
    public double calculateGPA(int studentId) {
        List<Grade> grades = gradeDao.findGradesByStudentId(studentId);
        double totalGradePoints = 0.0;
        double totalCredits = 0.0;

        for (Grade grade : grades) {
            double gradePoints = convertToGradePoints(grade.getGradeValue());
            int subjectId = grade.getSubject().getSubjectId();
            int  credits =subjectDao.findCreditHoursBySubjectId(subjectId);

            totalGradePoints += gradePoints * credits;
            totalCredits += credits;
        }

        if (totalCredits == 0) {
            return 0.0; // Avoid division by zero
        }

        return totalGradePoints / totalCredits;
    }

    public List<GradeReportDto> getGrades(int studentId) {
        List<Grade> grades = gradeDao.findGradesByStudentId(studentId);
        List<GradeReportDto> gradeReports = new ArrayList<>();

        for (Grade grade : grades) {
            GradeReportDto gradeReportDto = new GradeReportDto();
            gradeReportDto.setStudentId(studentId);
            gradeReportDto.setAssessmentGrade(grade.getAssessmentScore());
            gradeReportDto.setFinalExamGrade(grade.getFinalExamScore());
            gradeReportDto.setRemark(grade.getRemark());
            gradeReportDto.setSubjectCode(grade.getSubject().getSubjectCode());
            gradeReports.add(gradeReportDto);
        }

        return gradeReports;
    }


}
