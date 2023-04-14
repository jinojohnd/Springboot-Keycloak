package com.example.app.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.context.annotation.Profile;

@KeycloakConfiguration
@Profile(value = { "dev", "nossl" })
public class SecurityConfigurationDev extends SecurityConfiguration {

}
