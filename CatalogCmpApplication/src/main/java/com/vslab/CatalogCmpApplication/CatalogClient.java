package com.vslab.CatalogCmpApplication;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


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
        Product tmpproduct = restTemplate.getForObject("http://productCoreService:8080/product/" + productId, Product.class);
        productCache.putIfAbsent(productId, tmpproduct);
        return tmpproduct;
    }

    public Void addProduct(Product product) {
        return restTemplate.postForObject("http://productCoreService:8080/product", product, Void.class);
    }

    public List<Product> getProducts(String searchValue, Double maxPreis, Double minPreis) {
        List<Product> tmpproducts = restTemplate.getForObject(
                "http://productCoreService:8080/product" +
                        ((searchValue != null || maxPreis != null || minPreis != null) ? "?": "") +
                        (searchValue != null ? ("searchValue=" + searchValue + "&") : "") +
                        (maxPreis != null ? ("maxPreis=" + maxPreis + "&") : "") +
                        (minPreis != null ? ("minPreis=" + minPreis) : "")
                , List.class);

        return tmpproducts;
    }

    public boolean deleteProduct(Long productId) {
        try {
            restTemplate.delete("http://productCoreService:8080/product/" + productId);
            return true;
        } catch (HttpStatusCodeException error) {
            throw error;
        }
    }

    @HystrixCommand(fallbackMethod = "getCategoryCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Category getCategory(Long categoryId) {
        Category tmpcategory =
                restTemplate.getForObject("http://categoryCoreService:8082/category/" + categoryId, Category.class);
        categoryCache.putIfAbsent(categoryId, tmpcategory);
        return tmpcategory;
    }

    public Category addCategory(Category category) {
        Category tmpcategory = restTemplate.postForObject("http://categoryCoreService:8082/category", category, Category.class);
        return tmpcategory;
    }

    public boolean deleteCategory(Long categoryId) {
        try {
            restTemplate.delete("http://categoryCoreService:8082/category/" + categoryId);
            restTemplate.delete("http://productCoreService:8080/product/category/" + categoryId);
            return true;
        } catch (HttpStatusCodeException error) {
            // category wiederherstellen, wenn in Zeile 78 etwas schief geht?
            throw error;
        }
    }

    public Product getProductCache(Long productId) {
        return productCache.getOrDefault(productId, new Product());
    }

    public Category getCategoryCache(Long categoryId) {
        return categoryCache.getOrDefault(categoryId, new Category());
    }

}
