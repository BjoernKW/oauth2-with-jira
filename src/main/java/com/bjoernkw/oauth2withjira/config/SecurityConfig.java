package com.bjoernkw.oauth2withjira.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login-failure")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .loginPage("/")
                .redirectionEndpoint()
                .baseUri("/login/oauth/client/*")
                .and()
                .userInfoEndpoint()
                .userService(userService())
                .and()
                .defaultSuccessUrl("/login-success")
                .failureUrl("/login-failure");
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {
    return userRequest ->
            new OAuth2User() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return Collections.emptyList();
                }

                @Override
                public Map<String, Object> getAttributes() {
                    return new HashMap<>();
                }

                @Override
                public String getName() {
                    return "jira-user";
                }
            };
    }
}
