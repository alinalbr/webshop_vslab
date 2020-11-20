package com.vslab.ProductCoreApplication.Model;

import io.micrometer.core.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name="Product")
public class Product {

    //Primärschlüssel, dessen ID automatische erzeugt wird
    @Id
    @GeneratedValue
    private Long id;


    @NonNull
    private String name;

    @NonNull
    private Double price;

    @NonNull
    private Long categoryId;

    @NonNull
    private String details;

    public Product(String name, Double price, Long categoryId, String details) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.details = details;
    }

    public Product() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
