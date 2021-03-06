package com.vslab.CatalogCmpApplication;


public class Category {

    private Long id;
    private String name;


    public Category(String name) {
        this.name = name;
    }

    public Category() {

    }

    public boolean isEmptyObject() {
        return (
                    this.id == null
                    && this.name == null
                );
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
