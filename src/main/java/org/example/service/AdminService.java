package org.example.service;

import org.example.Dto.SubjectDto;
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
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AdminService {
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
    private EntityManager entityManager;

    public List<userDto> Users(int start, int max) {
        List<userDto> userDtos = new ArrayList<>();
        List<Users> users = userDao.listAll(start, max);

        users.forEach(user -> {
            userDto userDto = new userDto();
            userDto.setUserName(user.getUsername());
            userDto.setName(user.getName()); // Assuming User has a `getName()` method
            userDto.setRoleName(user.getRole().getName()); // Assuming User has a `Role` and Role has a `getName()`
            userDtos.add(userDto);
        });

        return userDtos;
    }

    public List<SubjectDto> Subjects(int start, int max) {
        List<SubjectDto> subjectDtos = new ArrayList<>();
        List<Subject> subjects = subjectDao.listAll(start, max);

        subjects.forEach(subject -> {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setName(subject.getSubjectName());
            subjectDto.setSubjectCode(subject.getSubjectCode());
            subjectDto.setSubjectId(subject.getSubjectId());

            Teacher teacher = subject.getTeacher();
            if (teacher != null) {
                Users user = teacher.getUser(); // Assuming Teacher has a `getUser` method
                if (user != null) {
                    subjectDto.setCurrentAssignedteacherName(user.getName()); // Assuming User has a `getName` method
                }
            }

            subjectDtos.add(subjectDto);
        });

        return subjectDtos;
    }

    public List<userDto> UsersRole(int roleId, int start, int max) {
        List<userDto> userDtos = new ArrayList<>();
        List<Users> users = userDao.listAllUsersByRole(roleId, start, max);

        users.forEach(user -> {
            userDto userDto = new userDto();
            userDto.setUserName(user.getUsername());
            userDto.setName(user.getName()); // Assuming User has a `getName()` method
            userDto.setRoleName(user.getRole().getName()); // Assuming User has a `Role` and Role has a `getName()`
            userDtos.add(userDto);
        });

        return userDtos;
    }

    public userDto getUserById(int id) {
        // Fetch the user from the database using the provided ID
        Users user = userDao.findById(id); // Assuming userDao has a method for fetching a user by ID

        // Check if the user exists
        if (user == null) {
            return null;  // Return null or throw an exception if the user is not found
        }

        // Convert the user entity to userDto
        userDto dto = new userDto();
        dto.setUserName(user.getUsername());
        dto.setName(user.getName());
        dto.setRoleName(user.getRole().getName()); // Ensure that the role name is available

        return dto;

    }
    @Transactional
    public RegistrationResponseDto addUser(UserReq userReq) {
        // Create and populate userModel with data from userReq
        Users userModel = new Users();
        userModel.setUsername(userReq.getUsername());
        userModel.setName(userReq.getName());
        userModel.setPassword(userReq.getPassword()); // Make sure to hash the password

        // Find the role
        Role role = roleDao.findById(userReq.getRoleId());
        if (role == null) {
            return new RegistrationResponseDto(false, "Role not found");
        }
        userModel.setRole(role);

        try {
            // Persist the user model and flush to get the ID
            int userId = userDao.create(userModel);
            em.flush(); // Ensure the user is persisted and the ID is generated

            // Verify that the user ID is set
            if (userModel.getId() == 0) {
                return new RegistrationResponseDto(false, "User ID not generated");
            }


            // Now create the Student if the role is student
               if (role.getId() == 3) { // Assuming roleId for student is 3
                     // Create student
                       Student student = new Student();
                       student.setUser(userModel); // Associate the user
                       student.setClassLevel(userReq.getClass_level()); // Set class level for the student

                     // Create student in the database
                       studentDao.create(student);
                       em.flush(); // Ensure the student is persisted
                }
//
             if (role.getId() == 2) { // Assuming roleId for Teacher id 3
                // Create teacher
                Teacher teacher = new Teacher();
                teacher.setUser(userModel); // Associate the user
                // Set class level for the student

                // Create teacher in the database
                teacherDao.create(teacher);
                em.flush(); // Ensure the student is persisted
            }


//                // Return success response
//                return new RegistrationResponseDto(true, "User created successfully")

        } catch (Exception e) {
            return new RegistrationResponseDto(false, "Error creating user and student: " + e.getMessage());

        }
        return new RegistrationResponseDto(true,"user created") ;

    }
    @Transactional
    public RegistrationResponseDto addSubject(SubjectRegisterDto subjectRegisterDto) {
        // Create and populate userModel with data from userReq
        Subject subjectModel = new Subject();
        subjectModel.setSubjectName(subjectRegisterDto.getName());
        subjectModel.setSubjectCode(subjectRegisterDto.getSubjectCode());
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















}









