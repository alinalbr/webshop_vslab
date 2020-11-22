package com.vslab.UserCmpApplication;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    @Autowired
    private  UserClient userClient;

    @Autowired
    private RestTemplate restTemplate;

    //Create User
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser( @RequestBody User user) {
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
    @RequestMapping(value = "users/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> loginUser( @RequestParam String username, @RequestParam String password) {


        ResponseEntity<Boolean> responseEntity;

          try {
              responseEntity = new ResponseEntity<>(userClient.loginUser(username, password), HttpStatus.OK);
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
    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> logoutUser( @RequestBody User user) {

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
