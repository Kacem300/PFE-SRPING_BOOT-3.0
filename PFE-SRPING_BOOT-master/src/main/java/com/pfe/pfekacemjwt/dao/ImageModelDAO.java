package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.imageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageModelDAO extends JpaRepository<imageModel, Long> {
}
