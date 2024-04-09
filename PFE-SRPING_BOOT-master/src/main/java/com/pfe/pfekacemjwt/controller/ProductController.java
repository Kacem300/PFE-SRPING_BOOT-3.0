package com.pfe.pfekacemjwt.controller;


import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.ProductSize;
import com.pfe.pfekacemjwt.entitiy.imageModel;

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


    @GetMapping("/getRandomProducts")
    public List<Product> getRandomProducts() {
        return productService.getRandomProducts(12);
    }
//    @PreAuthorize("hasRole('Admin')")
//@PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//public Product addNewProduct(@RequestPart("product") Product product,
//                             @RequestPart("sizes") Set<ProductSize> productSizes,
//                             @RequestPart("imageFile") MultipartFile[] file) {
//    try {
//        Set<imageModel> images = uploadImage(file);
//        product.setProductImages(images);
//        return productService.addNewProduct(product, productSizes);
//    } catch(Exception e){
//        System.out.println(e.getMessage());
//    }
//    return null;
//}




        @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart("product") Product product,
                                 @RequestPart("imageFile")MultipartFile[]file
    ){

        try {
            Set<imageModel> images = uploadImage(file);
            product.setProductImages(images);
            return productService.addNewProduct(product);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
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

    @GetMapping({"/getProductDetailsbyId/{productId}"})
    public Product getProductDetailsbyId(@PathVariable("productId") Integer productID){
        return productService.getProductID(productID);
    }


    @GetMapping({"/details/{single}/{productId}"})
    public List<Product> getProductDetails(@PathVariable(name = "single" ) boolean isSingleProductCheckout,
                                           @PathVariable(name = "productId")  Integer productId) {
        return productService.getProductDetails(isSingleProductCheckout, productId);
    }
}
