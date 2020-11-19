package com.vslab.UserSrvApplication.Model;

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
    private double price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @NonNull
    private String details;

    public Product(String name, double price, Category category, String details) {
        this.name = name;
        this.price = price;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
