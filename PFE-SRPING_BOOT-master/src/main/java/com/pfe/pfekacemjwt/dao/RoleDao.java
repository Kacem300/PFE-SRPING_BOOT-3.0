package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role,String> {

}
