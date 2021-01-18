package com.vslab.AuthorizationServer.controller;

/*import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;*/
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.security.Principal;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @RequestMapping(value = "/current")
    public Principal userInfo(@AuthenticationPrincipal Principal user) {
        return user;
    }

    @PostMapping(value="/ninchen")
    public ResponseEntity<String> helloNinchenToken(@RequestParam(required=false,name="token") String token) {
        System.out.println("POST " + token);
        return ResponseEntity.ok("Hallo Ninchen, das ist dein Token: " + token);
    }

    @GetMapping(value="/ninchen")
    public ResponseEntity<String> helloNinchenGetToken(@RequestParam(required=false,name="token") String token) {
        System.out.println("GET " + token);
        return ResponseEntity.ok("GET Hallo Ninchen, das ist dein Token: " + token);
    }

    /*@Autowired
    private JWKSet jwkSet;

    @GetMapping(value = "/oauth2/keys", produces = "application/json; charset=UTF-8")
    public String keys() {
        System.out.println("------------getting oauth keys");
        return this.jwkSet.toString();
    }*/

}
