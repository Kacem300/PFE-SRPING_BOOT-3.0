package com.pfe.pfekacemjwt.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class ProductGroups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productGroupsId;
   private String productGroupsName;
//       private String GroupsName;




    public ProductGroups() {

    }

    public Integer getProductGroupsId() {
        return productGroupsId;
    }

    public void setProductGroupsId(Integer productGroupsId) {
        this.productGroupsId = productGroupsId;
    }

//    public String getGroupsName() {
//        return GroupsName;
//    }
//
//    public void setGroupsName(String groupsName) {
//        GroupsName = groupsName;
//    }

        public String getProductGroupsName() {
        return productGroupsName;
    }

    public void setProductGroupsName(String productGroupsName) {
        this.productGroupsName = productGroupsName;
    }
}
