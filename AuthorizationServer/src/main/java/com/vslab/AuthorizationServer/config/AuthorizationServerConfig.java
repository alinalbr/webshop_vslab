package com.vslab.AuthorizationServer.config;

import com.vslab.AuthorizationServer.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices())
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .userDetailsService(userDetailsService);
        /*endpoints
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore())
                .userApprovalHandler(userApprovalHandler())
                .accessTokenConverter(accessTokenConverter());*/
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // configure security for /oauth/check_token and /oauth/token_key endpoint
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("webshop-client").secret("{noop}secretPassword")
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .scopes("read", "write");
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        // creates authorization codes, stores the codes in memory.
        return new InMemoryAuthorizationCodeServices();
    }



  /*

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
                .withClient("webshop-client")
                .authorizedGrantTypes("authorization_code", "refresh_token", "client_credentials", "password")
                .scopes("message.read", "message.write")
                .secret("{noop}secret")
                .redirectUris("http://localhost:8080/client/authorized");
        // @formatter:on
    }

    @Bean
    public UserApprovalHandler userApprovalHandler() {
        ApprovalStoreUserApprovalHandler userApprovalHandler = new ApprovalStoreUserApprovalHandler();
        userApprovalHandler.setApprovalStore(approvalStore());
        userApprovalHandler.setClientDetailsService(this.clientDetailsService);
        userApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(this.clientDetailsService));
        return userApprovalHandler;
    }

    @Bean
    public TokenStore tokenStore() {
        JwtTokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
        tokenStore.setApprovalStore(approvalStore());
        return tokenStore;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final RsaSigner signer = new RsaSigner(KeyConfig.getSignerKey());

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            private JsonParser objectMapper = JsonParserFactory.create();

            @Override
            protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String content;
                try {
                    content = this.objectMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
                } catch (Exception ex) {
                    throw new IllegalStateException("Cannot convert access token to JSON", ex);
                }
                Map<String, String> headers = new HashMap<>();
                headers.put("kid", KeyConfig.VERIFIER_KEY_ID);
                String token = JwtHelper.encode(content, signer, headers).getEncoded();
                return token;
            }
        };
        converter.setSigner(signer);
        converter.setVerifier(new RsaVerifier(KeyConfig.getVerifierKey()));
        return converter;
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new InMemoryApprovalStore();
    }

    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder(KeyConfig.getVerifierKey())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(KeyConfig.VERIFIER_KEY_ID);
        return new JWKSet(builder.build());
    }*/
}
