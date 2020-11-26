package com.vslab.CategoryCoreApplication.Model;

import javax.persistence.*;

import io.micrometer.core.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Category")
public class Category {

    //Primärschlüssel, dessen ID automatische erzeugt wird
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    //@NonNull
    //@ElementCollection
    //private List<Long> productIds;


    public Category(String name) {
        this.name = name;
        //this.productIds = new ArrayList<Long>();
    }

    public Category() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public void addProduct(Long productId) {
        this.productIds.add(productId);
    }

    public void removeProduct(Long productId) {
        this.productIds.remove(productId);
    }*/

}
