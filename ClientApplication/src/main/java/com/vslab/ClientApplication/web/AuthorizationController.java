package com.vslab.ClientApplication.web;

import com.vslab.ClientApplication.model.User;
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
    @Qualifier("webshopClientPasswordRestTemplate")
    private OAuth2RestTemplate webshopClientPasswordRestTemplate;

    @PostMapping(value = "/authorize")
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
            User user = this.webshopClientPasswordRestTemplate.getForObject(this.resourceBaseUri + "/user/" + passwordResourceDetails.getUsername(), User.class);
            model.addAttribute("eingeloggt", user.getFirstname());
        }
        else System.out.println("--------   access token = null");

        return "index";
    }
}
