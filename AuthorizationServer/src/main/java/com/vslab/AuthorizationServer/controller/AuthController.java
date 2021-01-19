package com.vslab.AuthorizationServer.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @RequestMapping(value = "/current")
    public Principal userInfo(@AuthenticationPrincipal Principal user) {
        return user;
    }

}
