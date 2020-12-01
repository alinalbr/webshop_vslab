package com.vslab.CatalogCmpApplication;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;


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
        Product tmpproduct =
                restTemplate.getForObject("http://product-core-service:8080/product/" + productId, Product.class);
        productCache.putIfAbsent(productId, tmpproduct);
        return tmpproduct;
    }

    @HystrixCommand(fallbackMethod = "getCategoryCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public Category getCategory(Long categoryId) {
        Category tmpcategory =
                restTemplate.getForObject("http://category-core-service:8082/category/" + categoryId, Category.class);
        categoryCache.putIfAbsent(categoryId, tmpcategory);
        return tmpcategory;
    }

    public Product getProductCache(Long userId) {
        return productCache.getOrDefault(userId, new Product());
    }

    public Category getCategoryCache(Long categoryId) {
        return categoryCache.getOrDefault(categoryId, new Category());
    }

}
