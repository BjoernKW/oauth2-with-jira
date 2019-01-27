package com.bjoernkw.oauth2withjira.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class ClientRegistrationConfig {

    private static final String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";

    private static final String JIRA_PROPERTY_KEY = "atlassian.jira.api.";

    private final Environment environment;

    @Autowired
    public ClientRegistrationConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<String> clients = Collections.singletonList("jira");

        List<ClientRegistration> registrations = clients
                .stream()
                .map(this::getRegistration)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration.Builder getJiraBuilder(String registrationId) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);

        builder.clientName(environment.getProperty(CLIENT_PROPERTY_KEY + "jira.client-name"));
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUriTemplate("{baseUrl}/{action}/oauth/client/jira");
        builder.scope(Objects.requireNonNull(environment.getProperty(CLIENT_PROPERTY_KEY + "jira.scope")).split(" "));
        builder.authorizationUri(environment.getProperty(CLIENT_PROPERTY_KEY + "jira.authorization-uri"));
        builder.tokenUri(environment.getProperty(CLIENT_PROPERTY_KEY + "jira.token-uri"));

        return builder;
    }

    private ClientRegistration getRegistration(String client) {
        String clientId = environment.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");
        String clientSecret = environment.getProperty(CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (clientId == null) {
            return null;
        }

        if (client.equals("jira")) {
            return getJiraBuilder(client)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();
        }

        return null;
    }
}
