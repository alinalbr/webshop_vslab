package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.RoleDAO;
import hska.iwi.eShopMaster.model.database.dataAccessObjects.UserDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Role;
import hska.iwi.eShopMaster.model.database.dataobjects.User;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author knad0001
 */

public class UserManagerImpl implements UserManager {
	private RestTemplate restTemplate = new RestTemplate();
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
		String user = "{'username': '" + username + "', 'password': '" + password + "'}";
		return this.restTemplate.postForObject("http://localhost:8086/auth/oauth/token", user, String.class);
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
