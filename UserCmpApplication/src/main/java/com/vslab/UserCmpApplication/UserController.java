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
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(
            @RequestBody
                    User user) {
        // pass new user to core service
        ResponseEntity<User> response = restTemplate.postForEntity(
                userClient.createUser(user) + "/user",
                user,
                User.class);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getStatusCode());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get user details by id.
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(
            @PathVariable
                    Long userId) {
        if (userId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // request user from core service
        ResponseEntity<User> response;

        try {
            response = restTemplate.exchange(
                    userClient.getUser() + "/users/" + id,
                    HttpMethod.GET,
                    null,
                    User.class
            );
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<User>(e.getStatusCode());
        }

        return response;
    }

    /**
     * Login User
     */

}
