package com.vslab.AuthorizationServer.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {
    @RequestMapping(value = "/current")
    public Principal userInfo(@AuthenticationPrincipal Principal user) {
        return user;
    }
}
