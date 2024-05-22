package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.ProductGroups;
import com.pfe.pfekacemjwt.entitiy.productCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductDao extends CrudRepository<Product,Integer> {
    @Query("SELECT p FROM Product p JOIN p.productGroups g WHERE g = :group")

    List<Product> findByProductGroups(@Param("group") ProductGroups group);

    List<Product> findByProductCategory(productCategory category);


    public List<Product> findByProductGroupsProductGroupsName(String groupName, Pageable pageable);
    public List<Product> findAll(Pageable pageable);
    public List<Product>findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCaseAndProductGroupsProductGroupsName(
            String key1, String key2, String groupName, Pageable pageable);
    public List<Product>findByProductCategoryCategoryNameAndProductGroupsProductGroupsName(String categoryName,String groupName,Pageable pageable);

    public List<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
            String key1, String key2, Pageable pageable);
    List<Product> findByProductCategoryCategoryName(String categoryName, Pageable pageable);



}


