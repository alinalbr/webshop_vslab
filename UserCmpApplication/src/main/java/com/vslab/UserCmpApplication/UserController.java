package com.vslab.UserCmpApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserClient userClient;

    @Autowired
    private RestTemplate restTemplate;

    //Create User
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // pass a new user to core service

        ResponseEntity<User> responseEntity = new ResponseEntity<>(userClient.createUser(user), HttpStatus.CREATED);

        if (responseEntity == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    //Get user
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // request user from core service with userClient
        ResponseEntity<User> responseEntity;

        try {
            responseEntity = new ResponseEntity<>(userClient.getUser(userId), HttpStatus.OK);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<User>(tp.getStatusCode());
        }

        return responseEntity;
    }

    //Login user
    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody User user) {
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(userClient.loginUser(user), HttpStatus.OK);

        if (responseEntity == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    //Logout user
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logoutUser(@RequestBody User user) {

        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(userClient.logoutUser(user), HttpStatus.OK);

        if (responseEntity == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}