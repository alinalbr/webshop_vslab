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
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long userId) {
        Optional<User> user = repo.findById(userId);
        if ( user.isPresent() ) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        if (!repo.existsByUsername(user.getUsername())) {
            User userCreated = repo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

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

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logoutWithUser(@RequestBody User user){
        boolean userLoggedOut = true;
        return new ResponseEntity<>(userLoggedOut, HttpStatus.OK);
    }



}
