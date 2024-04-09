package com.pfe.pfekacemjwt.service;

import com.pfe.pfekacemjwt.dao.RoleDao;
import com.pfe.pfekacemjwt.entitiy.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    public Role createNewRole(Role role){
        return roleDao.save(role);

    }
}
