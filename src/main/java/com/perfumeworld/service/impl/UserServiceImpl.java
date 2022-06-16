package com.perfumeworld.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.perfumeworld.dto.UserDto;
import com.perfumeworld.model.Role;
import com.perfumeworld.model.User;
import com.perfumeworld.repository.UserRepo;
import com.perfumeworld.service.UserService;
import com.perfumeworld.utils.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    //register
    @Override
    public UserDto saveUserService(UserDto userDto) {
        User user = userDto.convertToEntities();

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("account", userDto.getAccount());
        Object[] objects = userRepo.findByProperty(properties, null, null, null, null, null);
        Long total = (Long) objects[0];
        List<User> listEntity = (List<User>) objects[1];
        if (user.getId() == null) {
            if (total > 0) {
                userDto.setAlert("exist");
                return userDto;
            } else {
                user.setCreatedAt(new Date());
                user.setUpdatedAt(new Date());
                user = userRepo.insert(user);
                userDto = user.convertToDto();
            }
        } else {
            if (total > 0) {
                int id = listEntity.get(0).getId();
                if (id == userDto.getId()) {
                    user = userRepo.findById(user.getId());
                    user = user.convertToEntity(userDto);
                    user.setUpdatedAt(new Date());
                    user = userRepo.update(user);
                    userDto = user.convertToDto();
                } else {
                    userDto.setAlert("exist");
                    return userDto;
                }
            } else {
                user = userRepo.findById(user.getId());
                user = user.convertToEntity(userDto);
                user.setUpdatedAt(new Date());
                user = userRepo.update(user);
                userDto = user.convertToDto();
            }
        }
        return userDto;
    }

    @Override
    public String deleteUserService(Integer id) {
        UserDto dto = findByIdUserService(id);
        if (userRepo.delete(id)) {
            return "success";
        } else {
            return "fail";
        }
    }

    @Override
    public Boolean updateUserVerifyStatus(Integer id) {
        User user = userRepo.findById(id);
        user.setVerify(true);
        Role role = new Role();
        role.setId(2);
        user.setRole(role);
        if (user != null) {
            userRepo.update(user);
            return true;
        }
        return false;
    }


    @Override
    public Object[] viewPageUserService(int offset, int limit) {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        Object[] objects = userRepo.findByProperty(null, "updated_at", Constant.DESC, offset, limit, null);

        Long total = (Long) objects[0];
        List<User> listEntity = (List<User>) objects[1];

        for (User user : listEntity) {
            userDtos.add(user.convertToDto());
        }
        return new Object[]{total, userDtos};
    }

    @Override
    public UserDto findByIdUserService(Integer id) {
        User user = userRepo.findById(id);
        UserDto userDto = user.convertToDto();
        return userDto;
    }

    @Override
    public Object[] findFilterUserService(String[] filter) {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        Object[] objects = userRepo.findByProperty(null, "updated_at", Constant.DESC, null, null, filter);
        Long total = (Long) objects[0];
        List<User> listEntity = (List<User>) objects[1];
        for (User user : listEntity) {
            userDtos.add(user.convertToDto());
        }
        return new Object[]{total, userDtos};
    }
//login
    @Override
    public UserDto getUserByUserNameAndPassWordService(String account, String password) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("account", account);
        properties.put("password", password);
        Object[] objects = userRepo.findByProperty(properties, null, null, null, null, null);
        if (((List<User>) objects[1]).size() > 0) {
            return ((List<User>) objects[1]).get(0).convertToDto();
        }
        return null;
    }
}
