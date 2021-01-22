package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.opensymphony.xwork2.ActionContext;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.Product;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ProductManagerImpl implements ProductManager {
	private RestTemplate restTemplate = new RestTemplate();

	public ProductManagerImpl() {}

	public List<Product> getProducts() {
		ResponseEntity<Product[]> response = this.restTemplate.exchange("http://zuulserver:8085/product", HttpMethod.GET, getRequestEntity(), Product[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(response.getBody());
		} else {
			return null;
		}
	}
	
	public List<Product> getProductsForSearchValues(String searchDescription,
													Double searchMinPrice, Double searchMaxPrice) {
		ResponseEntity<Product[]> response = this.restTemplate.exchange("http://zuulserver:8085/product" +
						((!searchDescription.equals("") || searchMaxPrice != null || searchMinPrice != null) ? "?": "") +
						(!searchDescription.equals("") ? ("searchValue=" + searchDescription + "&") : "") +
						(searchMaxPrice != null ? ("maxPreis=" + searchMaxPrice + "&") : "") +
						(searchMinPrice != null ? ("minPreis=" + searchMinPrice) : ""), HttpMethod.GET, getRequestEntity(), Product[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return Arrays.asList(response.getBody());
		} else {
			return null;
		}
	}

	public Product getProductById(Long id) {
		ResponseEntity<Product> response = this.restTemplate.exchange("http://zuulserver:8085/product/" + id, HttpMethod.GET, getRequestEntity(), Product.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	public Product getProductByName(String name) { // this method is not needed
		return new Product("Test", 10.0, 1L, "details");
	}
	
	public Long addProduct(String name, double price, Long categoryId, String details) {
		Long productId = -1L;

		Product product;
		if(details == null){
			product = new Product(name, price, categoryId, "");
		} else{
			product = new Product(name, price, categoryId, details);
		}

		ResponseEntity<Product> response = this.restTemplate.exchange("http://zuulserver:8085/product", HttpMethod.POST, getRequestEntityWithBody(product), Product.class);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			Product createdProduct = response.getBody();
			productId = createdProduct.getId();
		}

		return productId;
	}
	

	public boolean deleteProductById(Long id) {
		ResponseEntity<Boolean> response = this.restTemplate.exchange("http://zuulserver:8085/product/" + id, HttpMethod.DELETE, getRequestEntity(), Boolean.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			if (response.getBody()) {
				return true;
			}
		}
		return false;
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

	public HttpEntity getRequestEntity() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + session.get("webshop_jwt"));
		return new HttpEntity<String>(headers);
	}

	public HttpEntity getRequestEntityWithBody(Product product) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + session.get("webshop_jwt"));
		return new HttpEntity(product, headers);
	}

}
