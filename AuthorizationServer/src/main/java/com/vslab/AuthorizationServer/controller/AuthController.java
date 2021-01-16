package com.vslab.AuthorizationServer.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthController {
    @GetMapping(value = "/ninchen", produces = "application/json; charset=UTF-8")
    public String ninchen() {
        return "Hallo Ninchen";
    }

    @RequestMapping(value = "/current")
    public Principal userInfo(@AuthenticationPrincipal Principal user) {
        return user;
    }

}
