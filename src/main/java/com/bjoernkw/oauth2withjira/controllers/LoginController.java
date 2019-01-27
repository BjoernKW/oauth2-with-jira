package com.bjoernkw.oauth2withjira.controllers;

import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    private static final String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";

    private static final String CLIENT_AUTHORIZATION_PATH= "/oauth2/authorization";

    private final Environment environment;

    private final ClientRegistrationRepository clientRegistrationRepository;

    private final OAuth2AuthorizedClientService authorizedClientService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public LoginController(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService,
            Environment environment) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientService = authorizedClientService;
        this.environment = environment;
    }

    @GetMapping("/")
    public String getLoginPage(Model model) {
        ClientRegistration clientRegistration = clientRegistrationRepository
                .findByRegistrationId(
                        Objects
                                .requireNonNull(environment.getProperty(CLIENT_PROPERTY_KEY + "jira.client-name"))
                                .toLowerCase()
                );

        model.addAttribute("url", CLIENT_AUTHORIZATION_PATH + "/" + clientRegistration.getRegistrationId());
        model.addAttribute("name", clientRegistration.getClientName());

        return "index";
    }

    @GetMapping("/login-success")
    public String getLoginInfo(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
        );

        logger.info("Jira access token: {}", client.getAccessToken().getTokenValue());

        return "login-success";
    }
}

