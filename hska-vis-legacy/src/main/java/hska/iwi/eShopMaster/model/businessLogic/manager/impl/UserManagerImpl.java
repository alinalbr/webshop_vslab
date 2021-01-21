package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.opensymphony.xwork2.ActionContext;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.User;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Map;

/**
 * 
 * @author knad0001
 */

public class UserManagerImpl implements UserManager {
	private OAuth2RestTemplate webshopAuthTemplate;
	private RestTemplate restTemplate = new RestTemplate();
	
	public UserManagerImpl() {}

	
	public void registerUser(String username, String name, String lastname, String password, Long role) {
		User user = new User(username, name, lastname, password, role);
		this.webshopAuthTemplate.postForObject("http://localhost:8085/user", user, User.class);
	}

	
	public String authorizeUser(String username, String password) {
		if (username == null || username.equals("")) {
			return null;
		}
		if (password == null || password.equals("")) {
			return null;
		}

		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setUsername(username);
		resourceDetails.setPassword(password);
		resourceDetails.setAccessTokenUri("http://auth:8086/auth/oauth/token");
		resourceDetails.setClientId("webshop-client");
		resourceDetails.setClientSecret("secretPassword");
		resourceDetails.setGrantType("password");
		resourceDetails.setScope(Arrays.asList("read", "write"));
		this.webshopAuthTemplate = new OAuth2RestTemplate(resourceDetails);

		return this.webshopAuthTemplate.getAccessToken().toString();
	}

	public User getUserByUsername(String username) {
		ResponseEntity<User> response = this.restTemplate.exchange("http://zuulserver:8085/user/" + username, HttpMethod.GET, getRequestEntity(), User.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			return null;
		}
	}

	public boolean deleteUserById(Long id) {
		this.webshopAuthTemplate.delete("http://localhost:8085/user/" + id);
		return true;
	}

	public boolean doesUserAlreadyExist(String username) {
    	User user = this.webshopAuthTemplate.getForObject("http://localhost:8085/user/" + username, User.class);
    	
    	if (user != null){
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	

	public boolean validate(User user) {
		if (user.getFirstname() == null || user.getPassword() == null || user.getRole() == null || user.getLastname() == null || user.getUsername() == null) {
			return false;
		}
		
		return true;
	}

	public HttpEntity getRequestEntity() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		System.out.println("session: " + session.get("webshop_jwt"));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "bearer " + session.get("webshop_jwt"));
		return new HttpEntity<String>(headers);
	}

}
