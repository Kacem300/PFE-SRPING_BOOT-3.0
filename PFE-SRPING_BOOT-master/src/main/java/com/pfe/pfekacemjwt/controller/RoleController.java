package com.pfe.pfekacemjwt.controller;

import com.pfe.pfekacemjwt.entitiy.Role;
import com.pfe.pfekacemjwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping({"/createdNewRole"})
    public Role createNewRole(@RequestBody Role role){
        return roleService.createNewRole(role);

    }
}
