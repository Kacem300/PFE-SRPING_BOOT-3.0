package com.pfe.pfekacemjwt.entitiy;

import jakarta.persistence.*;

import java.util.Set;



@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sizeValue; // This can be a number for shoes, or S/M/L for t-shirts
    @OneToMany(mappedBy = "size")
    private Set<ProductSize> productSizes;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(String sizeValue) {
        this.sizeValue = sizeValue;
    }

    public Set<ProductSize> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(Set<ProductSize> productSizes) {
        this.productSizes = productSizes;
    }


}
