package org.example.service;

import org.example.Dto.LoginDto;
import org.example.Dto.RegistrationRequestDto;
import org.example.Dto.RegistrationResponseDto;
import org.example.cassiomolin.security.domain.Authority;
import org.example.cassiomolin.security.service.PasswordEncoder;
import org.example.cassiomolin.user.api.resource.UserResource;
import org.example.cassiomolin.user.domain.User;
import org.example.cassiomolin.user.domain.UserAuthority;
import org.example.cassiomolin.user.domain.UserAuthorityId;
import org.example.cassiomolin.user.service.UserService;
import org.example.dao.UserDao;
import org.example.model.Role;
//import org.example.dao.UserDao;
//import org.example.model.User1;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Collections;

@Stateless
public class UserPasswordService {
    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    UserService userService;

    @Inject
    UserResource userResource;
    @EJB
    UserDao userDao;
    @Context
    private SecurityContext securityContext;
    public RegistrationResponseDto addUser(RegistrationRequestDto registerUserIn) {


            User userModel = new User();
            userModel.setId(null);
            userModel.setActive(true);
            userModel.setAuthorities(Collections.singleton(registerUserIn.getAuthorities()));
            userModel.setUsername(registerUserIn.getUsername());
            userModel.setUserGroup(1L);

         //registerUserIn.getAuthorities().equals(Authority.USER)
          if(registerUserIn.getPassword().equals(registerUserIn.getConfirmPassword()) ) {
              String hashedPassword = passwordEncoder.hashPassword(registerUserIn.getPassword());
              userModel.setPassword(hashedPassword);
              userDao.create(userModel);

              User user = new User();
              if (registerUserIn.getUsername().equals(user.getUsername())) {
                  return new RegistrationResponseDto(false, "user Name already exist");
              }
              // role.setRole();
              return new RegistrationResponseDto(true, "user Name created");

          }
              return new RegistrationResponseDto(false,"Unable to Register! please insert the same password ");


    }

        }
