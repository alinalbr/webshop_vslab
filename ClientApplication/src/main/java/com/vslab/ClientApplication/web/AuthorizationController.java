package com.vslab.ClientApplication.web;

import com.vslab.ClientApplication.model.Product;
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

    @GetMapping("/authorized")
    public String authorized(Model model) {

        return "start";
    }

    @PostMapping(value = "/authorize", params = "grant_type=password")
    public String password_grant(Model model, HttpServletRequest request) {
        System.out.println("Hallo aus authorize " + this.webshopClientPasswordRestTemplate.getResource());
        ResourceOwnerPasswordResourceDetails passwordResourceDetails =
                (ResourceOwnerPasswordResourceDetails) this.webshopClientPasswordRestTemplate.getResource();
        passwordResourceDetails.setUsername(request.getParameter("username"));
        passwordResourceDetails.setPassword(request.getParameter("password"));

        Product[] products = this.webshopClientPasswordRestTemplate.getForObject(this.resourceBaseUri + "/product", Product[].class);
        model.addAttribute("products", products);

        OAuth2AccessToken accessToken = this.webshopClientPasswordRestTemplate.getAccessToken();
        if (accessToken != null) {
            System.out.println("--------   accesstoken  " + accessToken);
        }
        else System.out.println("--------   access token = null");

        // Never store the user's credentials
        passwordResourceDetails.setUsername(null);
        passwordResourceDetails.setPassword(null);

        System.out.println("Bin am Ende");
        return "index";
    }
}
