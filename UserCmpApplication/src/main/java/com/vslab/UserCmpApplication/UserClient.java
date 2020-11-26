package com.vslab.UserCmpApplication;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserClient {
    private final Map<Long, User> userCache = new LinkedHashMap<Long, User>();

    @Autowired
    private RestTemplate restTemplate;

    //Get User
    //@HystrixCommand(fallbackMethod = "getUserCache", commandProperties = {
      //      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
    public User getUser(Long userId) {
        User tmpuser =
                restTemplate.getForObject("http://user-core-service:8081/user/" + userId, User.class);
        //userCache.putIfAbsent(userId, tmpuser);
        return tmpuser;
    }

    public User getUserCache(Long userId) {

        return userCache.getOrDefault(userId, new User());
    }

    //Create User
    public User createUser(User user) {
        User response =
                restTemplate.postForObject("http://user-core-service:8081/user", user, User.class);
        return response;
    }

    //Login User
    public Boolean loginUser(User user) {
        Boolean response =
                restTemplate.postForObject("http://user-core-service:8081/user/login", user, Boolean.class);
        return response;
    }

    //Logout User
    public Boolean logoutUser(User user) {
        Boolean response =
                restTemplate.postForObject("http://user-core-service:8081/user/logout", user, Boolean.class);
        return response;
    }


}