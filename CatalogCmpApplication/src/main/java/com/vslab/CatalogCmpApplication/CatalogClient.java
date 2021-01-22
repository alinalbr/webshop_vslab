package com.vslab.CatalogCmpApplication;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Component
public class CatalogClient {
    private final Map<Long, Product> productCache = new LinkedHashMap<Long, Product>();
    private final Map<Long, Category> categoryCache = new LinkedHashMap<Long, Category>();

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getProductCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Product getProduct(Long productId) {
        Product tmpproduct = restTemplate.getForObject("http://productCoreService/product/" + productId, Product.class);
        Category category = getCategory(tmpproduct.getCategoryId());
        if (!category.isEmptyObject()) {
            tmpproduct.setCategoryName(category.getName());
        } else {
            tmpproduct.setCategoryName("unknown category");
        }
        return tmpproduct;
    }

    public Product addProduct(Product product) {
        productCache.put(product.getId(), product);
        return restTemplate.postForObject("http://productCoreService/product", product, Product.class);
    }

    @HystrixCommand(fallbackMethod = "getAllProductCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Product[] getProducts(String searchValue, Double maxPreis, Double minPreis) {
        Product[] products;
        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity("http://productCoreService/product" +
                            ((searchValue != null || maxPreis != null || minPreis != null) ? "?": "") +
                            (searchValue != null ? ("searchValue=" + searchValue + "&") : "") +
                            (maxPreis != null ? ("maxPreis=" + maxPreis + "&") : "") +
                            (minPreis != null ? ("minPreis=" + minPreis) : "")
                    , Product[].class);

            products = response.getBody();

            for (Product product : products) {
                Category category = getCategory(product.getCategoryId());
                System.out.println("hallo " + product.getName());
                if (!category.isEmptyObject()) {
                    System.out.println("if");
                    product.setCategoryName(category.getName());
                } else {
                    System.out.println("else");
                    product.setCategoryName("unknown category");
                }
            }
            System.out.println("drunter");

            productCache.clear();
            for (Product product : products) {
                productCache.put(product.getId(), product);
            }

            return products;
        } catch (HttpStatusCodeException error) {
            throw error;
        }
    }

    public boolean deleteProduct(Long productId) {
        try {
            restTemplate.delete("http://productCoreService/product/" + productId);
            productCache.remove(productId);
            return true;
        } catch (HttpStatusCodeException error) {
            throw error;
        }
    }

    @HystrixCommand(fallbackMethod = "getCategoryCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Category getCategory(Long categoryId) {
        Category cat = restTemplate.getForObject("http://categoryCoreService/category/" + categoryId, Category.class);
        return cat;
    }

    public Category getCategoryByName(String categoryName) {
        Category cat = restTemplate.getForObject("http://categoryCoreService/category/name/" + categoryName, Category.class);
        return cat;
    }

    @HystrixCommand(fallbackMethod = "getAllCategoriesCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Category[] getCategories() {
        Category[] categories;
        try {
            ResponseEntity<Category[]> response = restTemplate.getForEntity("http://categoryCoreService/category", Category[].class);
            categories = response.getBody();

            categoryCache.clear();
            for (Category category : categories) {
                categoryCache.put(category.getId(), category);
            }

            return categories;
        } catch (HttpStatusCodeException error) {
            throw error;
        }
    }

    public Category addCategory(Category category) {
        categoryCache.put(category.getId(), category);
        Category tmpcategory = restTemplate.postForObject("http://categoryCoreService/category", category, Category.class);
        return tmpcategory;
    }

    public boolean deleteCategory(Long categoryId) {
        try {
            restTemplate.delete("http://categoryCoreService/category/" + categoryId);
            restTemplate.delete("http://productCoreService/product/category/" + categoryId);
            categoryCache.remove(categoryId);
            return true;
        } catch (HttpStatusCodeException error) {
            // category wiederherstellen, wenn in Zeile 78 etwas schief geht?
            throw error;
        }
    }

    public Product getProductCache(Long productId) {
        return productCache.getOrDefault(productId, new Product());
    }

    public Product[] getAllProductCache(String searchValue, Double maxPreis, Double minPreis) {
        List<Product> products = new ArrayList<Product>();
        for (Product product : productCache.values()) {
            products.add(product);
        }
        Product[] productArray = products.stream().toArray(Product[] ::new);
        return productArray;
    }

    public Category getCategoryCache(Long categoryId) {
        return categoryCache.getOrDefault(categoryId, new Category());
    }

    public Category[] getAllCategoriesCache() {
        List<Category> categories = new ArrayList<Category>();
        for (Category category : categoryCache.values()) {
            categories.add(category);
        }
        Category[] categoryArray = categories.stream().toArray(Category[] ::new);
        return categoryArray;
    }
}
