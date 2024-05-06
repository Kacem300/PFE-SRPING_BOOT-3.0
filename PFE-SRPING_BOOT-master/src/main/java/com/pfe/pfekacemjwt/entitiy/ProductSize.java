package com.pfe.pfekacemjwt.entitiy;
import jakarta.persistence.*;




@Entity
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ProductSizeId;
    private String size;
    private Integer quantity;



    public ProductSize() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

//    public Integer getSize() {
//        return size;
//    }
//
//    public void setSize(Integer size) {
//        this.size = size;
//    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductSizeId() {
        return ProductSizeId;
    }

    public void setProductSizeId(Integer productSizeId) {
        ProductSizeId = productSizeId;
    }


}

