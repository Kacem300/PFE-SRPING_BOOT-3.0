package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.ProductGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsDao extends JpaRepository<ProductGroups,Integer> {
}
