package com.perfumeworld.service;

import java.util.List;

import com.perfumeworld.dto.RoleDto;



public interface RoleService {
    List<RoleDto> getListRoleService();

    RoleDto getRoleById(int id);
}
