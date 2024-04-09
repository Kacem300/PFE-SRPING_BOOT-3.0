package com.pfe.pfekacemjwt.service;

import com.pfe.pfekacemjwt.configuration.jwtRequestFilter;
import com.pfe.pfekacemjwt.dao.CartDao;
import com.pfe.pfekacemjwt.dao.OrderDao;
import com.pfe.pfekacemjwt.dao.ProductDao;
import com.pfe.pfekacemjwt.dao.UserDao;
import com.pfe.pfekacemjwt.entitiy.Cart;
import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.ProductSize;
import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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



    public List<Product> getRandomProducts(int numberOfProducts) {
        List<Product> allProducts = (List<Product>) productDao.findAll();
        Collections.shuffle(allProducts);
        return allProducts.stream().limit(numberOfProducts).collect(Collectors.toList());
    }

//    public Product addNewProduct(Product product, Set<ProductSize> productSizes) {
//        product.setProductSizes(productSizes);
//        return productDao.save(product);
//    }


    public Product addNewProduct(Product product) {
        return productDao.save(product);
    }

    public List<Product> getAllProducts(int pageNumber,String keySearch) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        if(keySearch.isEmpty()){
            return (List<Product>) productDao.findAll(pageable);

        }else {
            return (List<Product>) productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(keySearch,keySearch,pageable);
        }
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
}
