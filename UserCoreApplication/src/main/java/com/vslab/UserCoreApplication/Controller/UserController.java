package com.vslab.UserSrvApplication.Controller;

import com.vslab.UserSrvApplication.Model.User;
import com.vslab.UserSrvApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = repo.findOne(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //POST User

    //POST User/login

    //POST User/logout

}
