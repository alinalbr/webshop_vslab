package com.vslab.UserCmpApplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .antMatchers(HttpMethod.GET, "/user/**").hasAuthority("SCOPE_read")
                        .antMatchers(HttpMethod.POST, "/user/login").hasAuthority("SCOPE_read")
                        .anyRequest().authenticated())
                .oauth2ResourceServer().jwt();
    }
}
