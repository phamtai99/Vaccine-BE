package com.project.vaccine.service;

import com.project.vaccine.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAllRole();

    void setDefaultRole(int accountId, Integer roleId);

    List<Role> getAllRoles();


}
