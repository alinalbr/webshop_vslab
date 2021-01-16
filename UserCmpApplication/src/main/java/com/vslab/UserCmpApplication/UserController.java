package com.vslab.UserCmpApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserClient userClient;

    @Autowired
    private RestTemplate restTemplate;

    //Create User
    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        try {
            userClient.createUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<>(tp.getStatusCode());
        }
    }

    //Get user
    /*@GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            User user = userClient.getUser(userId);
            if (!user.isEmptyObject()) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
            }
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<>(new User(), tp.getStatusCode());
        }

    }*/

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username, @AuthenticationPrincipal Jwt jwt) {
        String test = jwt.getClaims();
        System.out.println("===============================" + test);
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            User user = userClient.getUser(username);
            if (!user.isEmptyObject()) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
            }
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<>(new User(), tp.getStatusCode());
        }
    }

    //Login user
    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody User user) {
        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(userClient.loginUser(user), HttpStatus.OK);

        if (responseEntity == null) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    //Logout user
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logoutUser(@RequestBody User user) {

        ResponseEntity<Boolean> responseEntity = new ResponseEntity<>(userClient.logoutUser(user), HttpStatus.OK);

        if (responseEntity == null) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}