package com.vslab.zuul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class GatewayConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "oauth2-resource";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .antMatcher("/user/login")
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/user/login")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
