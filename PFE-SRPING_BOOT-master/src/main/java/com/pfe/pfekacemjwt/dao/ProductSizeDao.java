package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeDao extends JpaRepository<ProductSize, Long> {

}
