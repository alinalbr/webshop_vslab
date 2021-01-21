/*package hska.iwi.eShopMaster.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SuppressWarnings("deprecation")
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client.webshop-client-password")
    public OAuth2ProtectedResourceDetails webshopClientPasswordDetails() {
        return new ResourceOwnerPasswordResourceDetails();
    }

    @Bean
    public OAuth2RestTemplate webshopClientPasswordRestTemplate(
            @Qualifier("webshopClientPasswordDetails") OAuth2ProtectedResourceDetails resourceDetails
    ) {
        return new OAuth2RestTemplate(resourceDetails);
    }
}*/