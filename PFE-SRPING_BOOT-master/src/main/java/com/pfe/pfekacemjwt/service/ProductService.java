package com.pfe.pfekacemjwt.service;

import com.pfe.pfekacemjwt.configuration.jwtRequestFilter;
import com.pfe.pfekacemjwt.dao.*;
import com.pfe.pfekacemjwt.entitiy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final String Order_status = "Placed";
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductSizeDao productSizeDao;
    @Autowired
    private  RatingDao ratingDao;
    @Autowired
    private categoryDao categoryDao;
    @Autowired
    private GroupsDao groupsDao;

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getRandomProducts(int numberOfProducts) {
        List<Product> allProducts = (List<Product>) productDao.findAll();
        Collections.shuffle(allProducts);
        return allProducts.stream().limit(numberOfProducts).collect(Collectors.toList());
    }



    public List<Product> getAllProducts(int pageNumber,String keySearch) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        List<Product> products;
        if(keySearch.isEmpty()){
            products = (List<Product>) productDao.findAll(pageable);
        }else {
            products = (List<Product>) productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(keySearch,keySearch,pageable);
        }
        products.forEach(product -> product.getProductSizes().size()); // force initialization
        return products;
    }



    public Product getProductID(Integer productId) {
        return productDao.findById(productId).get();
    }
    public void deleteProductDetails(Integer productId) {
        productDao.deleteById(productId);
    }

    public List<Product> getProductDetails(boolean single, Integer productId) {
        if (single && productId != 0) {
            // we are going to buy a single product

            List<Product> list = new ArrayList<>();
            Product product = productDao.findById(productId).get();
            list.add(product);
            return list;
        } else {
            String username = jwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(username).get();
            List<Cart> carts = cartDao.findByUser(user);

            return carts.stream().map(x ->x.getProduct()).collect(Collectors.toList());


        }
    }


    public productCategory getCategoryById(Integer productCategoryId) {return categoryDao.findById(productCategoryId).get();}

    public void deleteProductCategory(Integer productCategoryId) {

        categoryDao.deleteById(productCategoryId);
    }



    public Double getAverageRating(Product product) {
        List<Rating> ratings = ratingDao.findByProduct(product);
        return ratings.stream().mapToInt(Rating::getRating).average().orElse(0.0);
    }

    public Rating saveRating(Rating rating) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User user = userDao.findById(currentUser).get();
        Rating existingRating = ratingDao.findByUserAndProduct(user, rating.getProduct());

        if (existingRating != null) {
            // If a rating from the same user for the same product already exists,
            // update the existing rating instead of creating a new one
            existingRating.setRating(rating.getRating());
            return ratingDao.save(existingRating);
        } else {
            // If no such rating exists, proceed as before
            rating.setUser(user);
            return ratingDao.save(rating);
        }
    }
    public Integer getUserRatingForProduct(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User user = userDao.findById(currentUser).get();
        Rating rating = ratingDao.findByUserAndProduct(user, product);
        return rating.getRating();
    }
    public List<productCategory> getCategories(){
        return categoryDao.findAll();
    }
    public List<ProductGroups> getGroups(){
        return groupsDao.findAll();
    }

    public productCategory addCategory(productCategory category){
        return categoryDao.save(category);
    }

    public ProductGroups addGroup(ProductGroups groups){
        return groupsDao.save(groups);
    }



}
