package com.vslab.UserCoreApplication.Controller;

import com.vslab.UserCoreApplication.Model.User;
import com.vslab.UserCoreApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repo;

    //GET User with userId
    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long userId) {
        Optional<User> user = repo.findById(userId);
        if ( user != null ) {
            return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
        }
        return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
    }


    //Add new User
    @PostMapping("")
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        if (!repo.existsByUsername(user.getUsername())) {
            User userCreated = repo.save(user);
            if (userCreated != null) {
                return new ResponseEntity<Void>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }


    //Login User with Pathvariables
    /*@PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody User user) {
        Optional<User> user = repo.findByUsername(username);
        boolean userLoggedIn = false;

        // Does user exist?
		if (user.isPresent()) {

        // Is the password correct?
            if (user.get().getPassword().equals(password)) {
                userLoggedIn = true;
                return new ResponseEntity<Boolean>(userLoggedIn, HttpStatus.OK);
            }
            return new ResponseEntity<Boolean>(userLoggedIn, HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<Boolean>(userLoggedIn, HttpStatus.CONFLICT);
    }*/

    //Login User with Pathvariables
    @PostMapping("/login")
    public ResponseEntity<Boolean> loginWithUserObject(@RequestBody User requestUser){

        Optional<User> user = repo.findByUsername(requestUser.getUsername());
        boolean userLoggedIn = false;

        // Does user exist?
        if (user != null) {

            // Is the password correct?
            if (user.get().getPassword().equals(requestUser.getPassword())) {
                userLoggedIn = true;
                return new ResponseEntity<>(userLoggedIn, HttpStatus.OK);
            }
            return new ResponseEntity<>(userLoggedIn, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userLoggedIn, HttpStatus.CONFLICT);
    }

    //Logout User without Parameters
    /*@PostMapping("/logout")
    public ResponseEntity<Boolean> logout(){
        boolean userLoggedOut = true;
        return new ResponseEntity<Boolean>(userLoggedOut, HttpStatus.OK);
    }*/

    //Logout User with Parameters
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResponseEntity<Boolean> logoutWithUser(@RequestBody User user){
        boolean userLoggedOut = true;
        return new ResponseEntity<>(userLoggedOut, HttpStatus.OK);
    }



}
