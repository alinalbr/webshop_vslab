package com.vslab.UserCmpApplication;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserClient {
    private final Map<String, User> userCache = new LinkedHashMap<String, User>();

    @Autowired
    private RestTemplate restTemplate;

    //Get User
    @HystrixCommand(fallbackMethod = "getUserCache", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    public User getUser(String username) {
        User tmpuser = restTemplate.getForObject("http://userCoreService/user/" + username, User.class);
        userCache.putIfAbsent(username, tmpuser);
        return tmpuser;
    }

    public User getUserCache(String username) {
        return userCache.getOrDefault(username, new User());
    }

    //Create User
    public Void createUser(User user) {
        return restTemplate.postForObject("http://userCoreService/user", user, Void.class);
    }

    //Login User
    public Boolean loginUser(User user) {
        boolean response =
                restTemplate.postForObject("http://userCoreService/user/login", user, Boolean.class);
        return response;
    }

    //Logout User
    public Boolean logoutUser(User user) {
        boolean response =
                restTemplate.postForObject("http://userCoreService/user/logout", user, Boolean.class);
        return response;
    }


}