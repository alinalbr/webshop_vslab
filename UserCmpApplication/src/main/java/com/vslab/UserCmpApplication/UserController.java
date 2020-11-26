package com.vslab.UserCmpApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

        ResponseEntity<User> responseEntity;
        responseEntity = new ResponseEntity<>(userClient.createUser(user), HttpStatus.OK);

        if (responseEntity == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);


        /*
        ResponseEntity<User> response = restTemplate.postForEntity(
                "http://user-core-service/user/",
                user,
                User.class);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
         */
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


        /*
        ResponseEntity<User> response;
        try {
            response = restTemplate.exchange(
                    "http://user-core-service/users/" + userId,
                    HttpMethod.GET,
                    null,
                    User.class
            );
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<User>(e.getStatusCode());
        }
        */

    }

    //Login user
    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestParam String username, @RequestParam String password) {


        ResponseEntity<Boolean> responseEntity;

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            responseEntity = new ResponseEntity<>(userClient.loginUser(user), HttpStatus.OK);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<>(tp.getStatusCode());
        }

        return responseEntity;

        /*
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        ResponseEntity<User> response = restTemplate.postForEntity(
                "http://user-core-service/user/login", user,
                User.class);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
         */
    }

    //Logout user
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logoutUser(@RequestBody User user) {

        ResponseEntity<Boolean> responseEntity;

        try {
            responseEntity = new ResponseEntity<>(userClient.logoutUser(user), HttpStatus.OK);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<>(tp.getStatusCode());
        }

        return responseEntity;

        /* ResponseEntity<User> response = restTemplate.postForEntity(
                "http://user-core-service/user/logout", user,
                User.class);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
         */
    }
}