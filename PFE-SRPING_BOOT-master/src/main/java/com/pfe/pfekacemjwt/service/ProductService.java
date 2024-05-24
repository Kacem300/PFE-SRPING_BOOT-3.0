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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    @Autowired
    private ImageModelDAO imageModelDAO;


    public imageModel saveImage(imageModel imageModel) {
        return imageModelDAO.save(imageModel);
    }

    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getRandomProducts(int numberOfProducts) {
        List<Product> allProducts = (List<Product>) productDao.findAll();
        Collections.shuffle(allProducts);
        return allProducts.stream().limit(numberOfProducts).collect(Collectors.toList());
    }


//    public List<Product> getProducts(int pageNumber, String keySearch, String categoryName) {
//        Pageable pageable = PageRequest.of(pageNumber, 8);
//        List<Product> products;
//
//        if (categoryName != null && !categoryName.isEmpty()) {
//            if (keySearch.isEmpty()) {
//                products = productDao.findByProductCategoryCategoryName(categoryName,pageable);
//            } else {
//                products = productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(keySearch, keySearch, pageable);
//
//            }
//        } else {
//            if (keySearch.isEmpty()) {
//                products = productDao.findAll(pageable);
//            } else {
//                products = productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(keySearch, keySearch, pageable);
//            }
//        }
//
//        products.forEach(product -> product.getProductSizes().size()); // force initialization
//        return products;
//    }
public List<Product> getProducts(int pageNumber, String keySearch, String categoryName, String productGroupsName) {
    Pageable pageable = PageRequest.of(pageNumber, 8);
    List<Product> products;

    if (productGroupsName != null && !productGroupsName.isEmpty()) {
        if (categoryName != null && !categoryName.isEmpty()) {
            if (keySearch.isEmpty()) {
                products = productDao.findByProductCategoryCategoryNameAndProductGroupsProductGroupsName(categoryName, productGroupsName, pageable);
            } else {
                products = productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCaseAndProductGroupsProductGroupsName(keySearch, keySearch, productGroupsName, pageable);
            }
        } else {
            if (keySearch.isEmpty()) {
                products = productDao.findByProductGroupsProductGroupsName(productGroupsName, pageable);
            } else {
                products = productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCaseAndProductGroupsProductGroupsName(keySearch, keySearch, productGroupsName, pageable);
            }
        }
    } else {
        if (categoryName != null && !categoryName.isEmpty()) {
            if (keySearch.isEmpty()) {
                products = productDao.findByProductCategoryCategoryName(categoryName,pageable);
            } else {
                products = productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(keySearch, keySearch, pageable);

            }
        } else {
            if (keySearch.isEmpty()) {
                products = productDao.findAll(pageable);
            } else {
                products = productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(keySearch, keySearch, pageable);
            }
        }
    }

    products.forEach(product -> product.getProductSizes().size()); // force initialization
    return products;
}





    public Product getProductID(Integer productId) {
        return productDao.findById(productId).get();
    }
//    public void deleteProductDetails(Integer productId) {
//        productDao.deleteById(productId);
//    }
@Transactional
public void deleteProductDetails(Integer productId) {
    orderDao.findByProduct_ProductId(productId).forEach(order -> {
        order.setProduct(null);
        orderDao.save(order);
    });
    // Now delete the product
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
    public productCategory addCategory(productCategory category){
        return categoryDao.save(category);
    }


    @Transactional
    public void deleteProductCategory(Integer productCategoryId) {
        productCategory category = categoryDao.findById(productCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product category ID: " + productCategoryId));

        // Remove associations with products
        List<Product> products = productDao.findByProductCategory(category);
        for (Product product : products) {
            product.setProductCategory(null);
            productDao.save(product);
        }

        // Now delete the category
        categoryDao.deleteById(productCategoryId);
    }

    @Transactional
    public void deleteProductGroup(Integer productGroupsId) {
        ProductGroups group = groupsDao.findById(productGroupsId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product group ID: " + productGroupsId));

        // Remove associations with products
        List<Product> products = productDao.findByProductGroups(group);
        for (Product product : products) {
            product.getProductGroups().remove(group);
            productDao.save(product);
        }

        // Now delete the group
        groupsDao.deleteById(productGroupsId);
    }

    //    public void deleteProductCategory(Integer productCategoryId) {
//
//        categoryDao.deleteById(productCategoryId);
//    }
//    public void deleteProductGroup(Integer productGroupsId) {
//
//        groupsDao.deleteById(productGroupsId);
//    }
    public productCategory updateCategory(productCategory category) {
        return categoryDao.save(category);
    }
    public ProductGroups updateGroup(ProductGroups groups) {
        return groupsDao.save(groups);
    }

    public List<productCategory> getCategories(){
        return categoryDao.findAll();
    }
    public List<ProductGroups> getGroups(){
        return groupsDao.findAll();
    }



    public ProductGroups addGroup(ProductGroups groups){
        return groupsDao.save(groups);
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

            existingRating.setRating(rating.getRating());
            return ratingDao.save(existingRating);
        } else {

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

    public List<Product> getTopOrderedProducts(int limit) {
        // Fetch all orders and convert Iterable to List
        Iterable<OrderDetail> iterable = orderDao.findAll();
        List<OrderDetail> orders = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());

        // Create a map to store the count of orders for each product
        Map<Product, Long> productOrderCount = new HashMap<>();

        // Iterate over the orders and increment the count for each product
        for (OrderDetail order : orders) {
            Product product = order.getProduct();
            productOrderCount.put(product, productOrderCount.getOrDefault(product, 0L) + 1);
        }

        // Sort the entries in the map in descending order based on the count
        List<Map.Entry<Product, Long>> sortedEntries = new ArrayList<>(productOrderCount.entrySet());
        sortedEntries.sort(Map.Entry.<Product, Long>comparingByValue().reversed());

        // Get the top 'limit' products
        List<Product> topProducts = sortedEntries.stream()
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return topProducts;
    }
    public List<Product> getTopRatedProducts(int limit) {
        // Fetch all products
        List<Product> allProducts = (List<Product>) productDao.findAll();

        // Create a map to store the average rating for each product
        Map<Product, Double> productAverageRating = new HashMap<>();

        // Iterate over the products and calculate the average rating for each product
        for (Product product : allProducts) {
            double averageRating = getAverageRating(product);
            productAverageRating.put(product, averageRating);
        }

        // Sort the entries in the map in descending order based on the average rating
        List<Map.Entry<Product, Double>> sortedEntries = new ArrayList<>(productAverageRating.entrySet());
        sortedEntries.sort(Map.Entry.<Product, Double>comparingByValue().reversed());

        // Get the top 'limit' products
        List<Product> topProducts = sortedEntries.stream()
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return topProducts;
    }


//    public List<Product> getProductsByCategory(String categoryName) {
//        return productDao.findByProductCategoryCategoryName(categoryName);
//    }

}
