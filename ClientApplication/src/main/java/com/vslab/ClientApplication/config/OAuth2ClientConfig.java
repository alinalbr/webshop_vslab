package com.vslab.ClientApplication.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SuppressWarnings("deprecation")
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
    @ConfigurationProperties(prefix = "security.oauth2.client.webshop-client-auth-code")
    @Bean
    public OAuth2ProtectedResourceDetails webshopClientAuthCodeDetails() {
        return new AuthorizationCodeResourceDetails();
    }

    @ConfigurationProperties(prefix = "security.oauth2.client.webshop-client-client-creds")
    @Bean
    public OAuth2ProtectedResourceDetails webshopClientClientCredsDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @ConfigurationProperties(prefix = "security.oauth2.client.webshop-client-password")
    @Bean
    public OAuth2ProtectedResourceDetails webshopClientPasswordDetails() {
        return new ResourceOwnerPasswordResourceDetails();
    }

    @Bean
    public OAuth2RestTemplate webshopClientAuthCodeRestTemplate(
            @Qualifier("webshopClientAuthCodeDetails") OAuth2ProtectedResourceDetails resourceDetails, OAuth2ClientContext oauth2ClientContext) {
        OAuth2ClientContext myoauth2ClientContext = oauth2ClientContext;
        return new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
    }

    @Bean
    public OAuth2RestTemplate webshopClientClientCredsRestTemplate(
            @Qualifier("webshopClientClientCredsDetails") OAuth2ProtectedResourceDetails resourceDetails) {
        return new OAuth2RestTemplate(resourceDetails);
    }

    @Bean
    public OAuth2RestTemplate webshopClientPasswordRestTemplate(
            @Qualifier("webshopClientPasswordDetails") OAuth2ProtectedResourceDetails resourceDetails) {
        return new OAuth2RestTemplate(resourceDetails);
    }
}
