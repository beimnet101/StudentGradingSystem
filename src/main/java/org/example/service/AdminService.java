package org.example.service;

import org.example.Dto.*;
import org.example.Dto.req.RegistrationResponseDto;
import org.example.Dto.req.UserReq;
import org.example.dao.RoleDao;
import org.example.dao.UsersDao;
import org.example.model.Role;
import org.example.model.Users;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AdminService {
    @EJB
    UsersDao userDao;
    @EJB
    RoleDao roleDao;


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
    @Transactional
    public RegistrationResponseDto addUser(UserReq userReq) {
        // Create a new Users entity
        Users userModel = new Users();

        // Map fields from UserReq to Users
        userModel.setUsername(userReq.getUsername());
        userModel.setName(userReq.getName());

        // Set the password (make sure to hash it if storing securely)
        //String hashedPassword = passwordEncoder.hashPassword(userReq.getPassword());
        userModel.setPassword(userReq.getPassword());

        // Fetch and set the Role entity
        Role role= roleDao.findById(userReq.getRoleId());
        if (role == null) {
            return new RegistrationResponseDto(false, "Role not found");
        }

        userModel.setRole(role);

        // Check if the username already exists
        //if (userDao.findByUsername(userReq.getUsername()) != null) {
        //return new RegistrationResponseDto(false, "Username already exists");
        userDao.create(userModel);


        return new RegistrationResponseDto(true, "User created successfully");
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









}
