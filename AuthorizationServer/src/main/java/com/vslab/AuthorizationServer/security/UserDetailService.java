package com.vslab.AuthorizationServer.security;

import com.vslab.AuthorizationServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class UserDetailService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    //@Autowired
    //private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("Username must not be null.");
        }

        // request user from core service
        ResponseEntity<User> response;

        try {
            response = restTemplate.exchange(
                    "http://userCoreService/user/" + username,
                    HttpMethod.GET,
                    null,
                    User.class
            );
        } catch (HttpStatusCodeException e) {
            throw new UsernameNotFoundException("Error retrieving user from user service.");
        }

        User user = response.getBody();
        System.out.println("HAAAAAAAAAAAAAAAAAAAAAALLOOOOOOOOOOOOOOOOOO " + user);
        if (user == null) {
            throw new UsernameNotFoundException("Retrieved user is null.");
        }

        user.setPassword("{noop}" + user.getPassword());

        return user;
    }

}