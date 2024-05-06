package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.productCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryDao extends JpaRepository<productCategory, Integer> {
}
