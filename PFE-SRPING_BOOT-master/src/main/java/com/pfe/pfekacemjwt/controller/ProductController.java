package com.pfe.pfekacemjwt.controller;


import com.pfe.pfekacemjwt.dao.GroupsDao;
import com.pfe.pfekacemjwt.dao.categoryDao;
import com.pfe.pfekacemjwt.entitiy.*;

import com.pfe.pfekacemjwt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ProductController {



    @Autowired
    private ProductService productService;
    @Autowired
    private categoryDao categoryDao;
    @Autowired
    private GroupsDao groupsDao;

    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart("product") Product product,
                                 @RequestPart("sizes") Set<ProductSize> productSizes,
                                 @RequestPart("productCategoryId") Integer productCategoryId,
                                 @RequestPart("productGroupIds") Set<Integer> productGroupIds,
                                 @RequestPart("imageFile") MultipartFile[] file) {
        try {
            Set<imageModel> images = uploadImage(file);
            product.setProductImages(images);
            product.setProductSizes(productSizes);

            productCategory existingCategory = categoryDao.findById(productCategoryId).orElse(null);
            if (existingCategory != null) {
                product.setProductCategory(existingCategory);
            }

            Set<ProductGroups> productGroups = new HashSet<>();
            for(Integer id : productGroupIds) {
                ProductGroups group = groupsDao.findById(id).orElse(null);
                if(group != null) {
                    productGroups.add(group);
                }
            }
            product.setProductGroups(productGroups);

            return productService.addNewProduct(product);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
// The productGroups code is commented out in your original code.
    // If you want to use it, uncomment the following lines and make sure the groupIds are provided correctly.
    // List<ProductGroups> groups = groupsDao.findAllById(groupIds);
    // product.setProductGroups(new HashSet<>(groups));


    @GetMapping("/getRandomProducts")
    public List<Product> getRandomProducts() {
        return productService.getRandomProducts(12);
    }


    public Set<imageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set <imageModel> imageModels = new HashSet<>();
        for (MultipartFile file:multipartFiles){
            imageModel imageModel = new imageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }
        return imageModels;
    }

    @GetMapping("/getAllProduct")
    public List<Product> getAllProduct(@RequestParam (defaultValue = "0")int pageNumber,
                                       @RequestParam(defaultValue = "") String keySearch){
        return productService.getAllProducts(pageNumber,keySearch);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/deleteProductDetails/{productId}")
    public void deleteProductDetails(@PathVariable("productId") Integer productId){
        productService.deleteProductDetails(productId);
    }
    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/deleteProductCategory/{productCategoryId}")
    public void deleteProductCategory(@PathVariable("productCategoryId") Integer productCategoryId){
        productService.deleteProductCategory(productCategoryId);
    }
    @GetMapping({"/getProductDetailsbyId/{productId}"})
    public Product getProductDetailsbyId(@PathVariable("productId") Integer productID){
        return productService.getProductID(productID);
    }
    @GetMapping({"/getCategoryById/{productId}"})
    public productCategory getCategoryById(@PathVariable("productId") Integer productCategoryId){
        return productService.getCategoryById(productCategoryId);
    }


    @GetMapping({"/details/{single}/{productId}"})
    public List<Product> getProductDetails(@PathVariable(name = "single" ) boolean isSingleProductCheckout,
                                           @PathVariable(name = "productId")  Integer productId) {
        return productService.getProductDetails(isSingleProductCheckout, productId);
    }

    @PostMapping("/rateProduct/{productId}")
    public Rating rateProduct(@PathVariable("productId") Integer productId, @RequestBody Rating rating) {
        Product product = productService.getProductID(productId);
        rating.setProduct(product);
        return productService.saveRating(rating); // you'll need to create this service method
    }
    @GetMapping("/product/{productId}")
    public Double getProductDetails(@PathVariable("productId") Integer productId) {
        Product product = productService.getProductID(productId);
        Double averageRating = productService.getAverageRating(product);
        return averageRating;
    }

    @GetMapping("/userRating/{productId}")
    public Integer getUserRatingForProduct(@PathVariable("productId") Integer productId) {
        Product product = productService.getProductID(productId);
        return productService.getUserRatingForProduct(product);
    }

    @GetMapping("/getCategories")
    public List<productCategory> getCategories() {
        return productService.getCategories();
    }
    @GetMapping("/getGroups")
    public List<ProductGroups> getGroups() {
        return productService.getGroups();
    }

    @PostMapping("/addCategory")
    public productCategory addCategory(productCategory category) {
        return productService.addCategory(category);
    }
    @PostMapping("/addGroup")
    public ProductGroups addGroup(ProductGroups productGroups){
        return productService.addGroup(productGroups);
    }


}
