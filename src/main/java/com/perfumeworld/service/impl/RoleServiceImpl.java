package com.perfumeworld.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumeworld.dto.RoleDto;
import com.perfumeworld.model.Role;
import com.perfumeworld.repository.RoleRepo;
import com.perfumeworld.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;

    @Override
    public List<RoleDto> getListRoleService() {
        List<RoleDto> dtos = new ArrayList<RoleDto>();
        List<Role> entities = roleRepo.findAll();
        for (Role role : entities) {
            RoleDto dto = role.convertToDto();
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public RoleDto getRoleById(int id) {
        return roleRepo.findById(id).convertToDto();
    }
}
