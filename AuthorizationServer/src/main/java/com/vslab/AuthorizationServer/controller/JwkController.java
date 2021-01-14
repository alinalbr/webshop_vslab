package com.vslab.AuthorizationServer.controller;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class JwkController {
    @RequestMapping(value = "/current")
    public Principal userInfo(@AuthenticationPrincipal Principal user) {
        return user;
    }

    /**
    @Autowired
    private JWKSet jwkSet;

    @GetMapping(value = "/oauth2/keys", produces = "application/json; charset=UTF-8")
    public String keys() {
        System.out.println("------------getting oauth keys");
        return this.jwkSet.toString();
    }
    */
}
