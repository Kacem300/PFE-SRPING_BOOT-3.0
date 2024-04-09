package com.pfe.pfekacemjwt.entitiy;
import jakarta.persistence.*;

import java.util.Set;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    @Column(length = 1000)
    private String productDescription;
    private Double productDiscountprice;
    private Double productActualprice;
    private Integer productNote;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "product_images",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn (name = "image_id")
            }
    )
    private Set<imageModel> productImages;
//    @OneToMany(mappedBy = "product")
//    private Set<ProductSize> productSizes;



//    public Set<ProductSize> getProductSizes() {
//        return productSizes;
//    }
//
//    public void setProductSizes(Set<ProductSize> productSizes) {
//        this.productSizes = productSizes;
//    }

    public Set<imageModel> getProductImages() {
        return productImages;
    }

    public void setProductImages(Set<imageModel> productImages) {
        this.productImages = productImages;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductDiscountprice() {
        return productDiscountprice;
    }

    public void setProductDiscountprice(Double productDiscountprice) {
        this.productDiscountprice = productDiscountprice;
    }

    public Double getProductActualprice() {
        return productActualprice;
    }

    public void setProductActualprice(Double productActualprice) {
        this.productActualprice = productActualprice;
    }

    public Integer getProductNote() {
        return productNote;
    }

    public void setProductNote(Integer productNote) {
        this.productNote = productNote;
    }
}
