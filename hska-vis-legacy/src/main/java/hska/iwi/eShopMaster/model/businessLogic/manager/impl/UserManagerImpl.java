package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.RoleDAO;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.UserDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Role;
import hska.iwi.eShopMaster.model.database.dataobjects.User;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * 
 * @author knad0001
 */

public class UserManagerImpl implements UserManager {
	private OAuth2RestTemplate webshopAuthTemplate;

	UserDAO helper;
	
	public UserManagerImpl() {
		helper = new UserDAO();
	}

	
	public void registerUser(String username, String name, String lastname, String password, Role role) {

		User user = new User(username, name, lastname, password, role);

		helper.saveObject(user);
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

	public boolean deleteUserById(int id) {
		User user = new User();
		user.setId(id);
		helper.deleteObject(user);
		return true;
	}

	public Role getRoleByLevel(int level) {
		RoleDAO roleHelper = new RoleDAO();
		return roleHelper.getRoleByLevel(level);
	}

	public boolean doesUserAlreadyExist(String username) {
		return false;
/*    	User dbUser = this.getUserByUsername(username);
    	
    	if (dbUser != null){
    		return true;
    	}
    	else {
    		return false;
    	}*/
	}
	

	public boolean validate(User user) {
		/*if (user.getFirstname().isEmpty() || user.getPassword().isEmpty() || user.getRole() == null || user.getLastname() == null || user.getUsername() == null) {
			return false;
		}*/
		
		return true;
	}

}
