package com.vslab.ClientApplication.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("deprecation")
@Controller
public class AuthorizationController {
    @Value("${webshop.base-uri}")
    private String resourceBaseUri;

    @Autowired
    @Qualifier("webshopClientClientCredsRestTemplate")
    private OAuth2RestTemplate webshopClientAuthCodeRestTemplate;

    @Autowired
    @Qualifier("webshopClientClientCredsRestTemplate")
    private OAuth2RestTemplate webshopClientClientCredsRestTemplate;

    @Autowired
    @Qualifier("webshopClientPasswordRestTemplate")
    private OAuth2RestTemplate webshopClientPasswordRestTemplate;

    @GetMapping(value = "/authorize", params = "grant_type=authorization_code")
    public String authorization_code_grant(Model model) {
        System.out.println("-------/authorize  " + this.webshopClientAuthCodeRestTemplate.getOAuth2ClientContext().getAccessToken());
        System.out.println("-------/authorize  " + this.webshopClientAuthCodeRestTemplate.getOAuth2ClientContext().getAccessTokenRequest());
        return "index";
    }

    @GetMapping("/authorized")		// registered redirect_uri for authorization_code
    public String authorized(Model model) {
        System.out.println("-------/authorized ---"  + this.webshopClientAuthCodeRestTemplate.getOAuth2ClientContext().getAccessToken());
        System.out.println("-------/authorize  " + this.webshopClientAuthCodeRestTemplate.getOAuth2ClientContext().getAccessTokenRequest());
        return "index";
    }

    @GetMapping(value = "/authorize", params = "grant_type=client_credentials")
    public String client_credentials_grant(Model model) {
        return "index";
    }

    @PostMapping(value = "/authorize", params = "grant_type=password")
    public String password_grant(Model model, HttpServletRequest request) {
        ResourceOwnerPasswordResourceDetails passwordResourceDetails =
                (ResourceOwnerPasswordResourceDetails) this.webshopClientPasswordRestTemplate.getResource();
        passwordResourceDetails.setUsername(request.getParameter("username"));
        passwordResourceDetails.setPassword(request.getParameter("password"));

        // Never store the user's credentials
        passwordResourceDetails.setUsername(null);
        passwordResourceDetails.setPassword(null);

        OAuth2AccessToken accessToken = this.webshopClientPasswordRestTemplate.getAccessToken();
        if (accessToken != null) {
            System.out.println("--------   accesstoken  " + accessToken);
        }
        else System.out.println("--------   access token = null");


        return "index";
    }
}
