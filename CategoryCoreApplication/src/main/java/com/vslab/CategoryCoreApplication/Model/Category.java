package com.vslab.CategoryCoreApplication.Model;

import javax.persistence.*;

import io.micrometer.core.lang.NonNull;

@Entity
@Table(name="Category")
public class Category {

    //Primärschlüssel, dessen ID automatische erzeugt wird
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;


    public Category(String name) {
        this.name = name;
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

}
