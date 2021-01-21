package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.ProductManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.ProductDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ProductManagerImpl implements ProductManager {
	private final RestTemplate restTemplate = new RestTemplate();

	private final ProductDAO helper;
	
	public ProductManagerImpl() {
		helper = new ProductDAO();
	}

	public List<Product> getProducts() {
		return helper.getObjectList();
	}
	
	public List<Product> getProductsForSearchValues(String searchDescription,
													Double searchMinPrice, Double searchMaxPrice) {
		final Product[] results = this.restTemplate.getForObject("http://localhost:8090/product", Product[].class);

		return Arrays.asList(results);
	}

	public Product getProductById(int id) {
		return helper.getObjectById(id);
	}

	public Product getProductByName(String name) {
		return helper.getObjectByName(name);
	}
	
	public int addProduct(String name, double price, int categoryId, String details) {
		int productId = -1;
		
		CategoryManager categoryManager = new CategoryManagerImpl();
		Category category = categoryManager.getCategory(categoryId);
		
		if(category != null){
			Product product;
			if(details == null){
				product = new Product(name, price, category);	
			} else{
				product = new Product(name, price, category, details);
			}
			
			helper.saveObject(product);
			productId = product.getId();
		}
			 
		return productId;
	}
	

	public void deleteProductById(int id) {
		helper.deleteById(id);
	}

	public boolean deleteProductsByCategoryId(int categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
