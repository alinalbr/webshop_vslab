package com.vslab.UserCoreApplication.Controller;

import com.vslab.UserCoreApplication.Model.User;
import com.vslab.UserCoreApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    //GET User with userId
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        User user = repo.findOne(userId);
        if ( user != null ) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }


    //Add new User
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(
            @RequestBody User user) {
        if (!repo.existsById(user.getId())) {
            repo.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.CONFLICT);
    }


    //Login User
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<Boolean> login(@PathVariable String username, @PathVariable String password){
        User user = repo.findByUsername(username);
        boolean userLoggedIn = false;

        // Does user exist?
		if (user != null) {

        // Is the password correct?
            if (user.getPassword().equals(password)) {
                userLoggedIn = true;
                return new ResponseEntity<>(userLoggedIn, HttpStatus.OK);
            }
            return new ResponseEntity<>(userLoggedIn, HttpStatus.CONFLICT);
		}
        return new ResponseEntity<>(userLoggedIn, HttpStatus.CONFLICT);

    }

    //Logout User
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResponseEntity<Boolean> logout(){
        boolean userLoggedOut = true;
        return new ResponseEntity<>(userLoggedOut, HttpStatus.OK);
    }



}




