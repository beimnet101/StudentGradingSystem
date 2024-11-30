package org.example.service;

import org.example.Dto.*;
import org.example.dao.UsersDao;
import org.example.model.Users;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class AdminService {
    @EJB
    UsersDao userDao;

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

}
