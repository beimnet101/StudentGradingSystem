package org.example.service;
import org.example.Dto.SubjectDto;
import org.example.Dto.req.GiveGradeDto;
import org.example.Dto.req.RegistrationResponseDto;
import org.example.Dto.req.SubjectRegisterDto;
import org.example.Dto.req.UserReq;
import org.example.Dto.userDto;
import org.example.dao.*;
import org.example.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.xml.registry.infomodel.User;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.example.utility.NumericGrToLetterGrade.convertToLetterGrade;

@Stateless
public class TeacherService {
    @PersistenceContext
    private EntityManager em;
    @EJB
    UserDao userDao;
    @EJB
    RoleDao roleDao;
    @EJB
    StudentDao studentDao;

    @EJB
    SubjectDao subjectDao;


    @EJB
    TeacherDao teacherDao;
    @EJB
    GradeDao gradeDao;

    private EntityManager entityManager;


    @Transactional
    public RegistrationResponseDto gradeYourSubject(GiveGradeDto giveGradeDto) {

        try {

            Subject subject = subjectDao.findBySubjectCode(giveGradeDto.getSubjectCode());

            if (subject == null) {
                throw new IllegalArgumentException("Subject not found with code: " + giveGradeDto.getSubjectCode());
            }


            if (subject.getTeacher().getTeacherId() != giveGradeDto.getTeacherId()) {
                throw new SecurityException("Teacher is not authorized to grade this subject.");
            }

            Student student = studentDao.findById(giveGradeDto.getStudentId());
            if (student == null) {
                throw new IllegalArgumentException("Student not found with ID: " + giveGradeDto.getStudentId());
            }
            Grade gradeModel = gradeDao.findByStudentAndSubject(giveGradeDto.getStudentId(), giveGradeDto.getSubjectCode());

            double totalScore = giveGradeDto.getAssessmentGrade() + giveGradeDto.getFinalExamGrade();
            String gradeLetter = convertToLetterGrade(totalScore);

            if (gradeModel == null) {
                gradeModel = new Grade(); // Create a new grade record

                gradeModel.setSubject(subject);
                gradeModel.setStudent(student);
                gradeModel.setDateAssigned(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                gradeModel.setRemark(giveGradeDto.getRemark());
                gradeModel.setAssessmentScore(giveGradeDto.getAssessmentGrade());
                gradeModel.setFinalExamScore(giveGradeDto.getFinalExamGrade());
                gradeModel.setTotalScore(totalScore);
                gradeModel.setGradeValue(gradeLetter);

             // Persist the grade (or update if it already exists)
              gradeDao.saveOrUpdate(gradeModel);
            }
            else {
                gradeModel.setDateAssigned(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                gradeModel.setRemark(giveGradeDto.getRemark());
                gradeModel.setAssessmentScore(giveGradeDto.getAssessmentGrade());
                gradeModel.setFinalExamScore(giveGradeDto.getFinalExamGrade());
                gradeModel.setTotalScore(totalScore);
                gradeModel.setGradeValue(gradeLetter);
              gradeDao.update(gradeModel);
            }

            // Flush changes to the database
            em.flush();

            // Return a success response
            return new RegistrationResponseDto(true, "Student grade posted or updated successfully.");
        } catch (Exception e) {
            // Return error response if any exception occurs
            return new RegistrationResponseDto(false, "Error posting grade: " + e.getMessage());
        }
    }




    @Transactional
    public RegistrationResponseDto updateSubjectTeacher(String subjectCode, int newTeacherId) {
        try {
            // Find the subject by ID
            Subject subject = subjectDao.findBySubjectCode(subjectCode);
            if (subject == null) {
                return new RegistrationResponseDto(false, "Subject not found");
            }

            // Find the new teacher
            Teacher newTeacher = teacherDao.findById(newTeacherId);
            if (newTeacher == null) {
                return new RegistrationResponseDto(false, "New teacher not found");
            }

            // Assign the new teacher to the subject
            subject.setTeacher(newTeacher);

            // Update the subject in the database
            subjectDao.update(subject);
            em.flush(); // Ensure changes are persisted

            return new RegistrationResponseDto(true, "Subject teacher updated successfully");
        } catch (Exception e) {
            return new RegistrationResponseDto(false, "Error updating subject teacher: " + e.getMessage());
        }
    }

    @Transactional
    public RegistrationResponseDto addSubject(SubjectRegisterDto subjectRegisterDto) {
        // Create and populate userModel with data from userReq
        Subject subjectModel = new Subject();
        subjectModel.setSubjectName(subjectRegisterDto.getName());
        subjectModel.setSubjectCode(subjectRegisterDto.getSubjectCode());
        subjectModel.setCredithour(subjectRegisterDto.getCredithour());
        Teacher teacher= teacherDao.findById(subjectRegisterDto.getTeacherId());
        subjectModel.setTeacher(teacher);
        try {
            // Persist the user model and flush to get the ID
            int subjectId = subjectDao.create(subjectModel);
            em.flush(); // Ensure the user is persisted and the ID is generated

            // Verify that the ID is set
            if (subjectId==0) {
                return new RegistrationResponseDto(false, "subject ID not generated");
            }

        } catch (Exception e) {
            return new RegistrationResponseDto(false, "Error creating user and student: " + e.getMessage());

        }

        return new RegistrationResponseDto(true,"Subject created") ;

    }

}