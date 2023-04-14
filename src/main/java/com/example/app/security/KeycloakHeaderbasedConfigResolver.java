package com.example.app.security;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class KeycloakHeaderbasedConfigResolver extends KeycloakSpringBootConfigResolver {

	private AdapterConfig adapterConfig;

	private final ConcurrentHashMap<String, KeycloakDeployment> cache = new ConcurrentHashMap<>();

	private KeycloakDeployment keycloakDeployment;

	@Value("${app.base.dir}")
	private String appBase;

	@Value("${keycloak.realm}")
	private String realm;

	@Override
	public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
		String realm = request.getHeader("x-realm");
		if (StringUtils.isBlank(realm)) {
			realm = this.realm;
		}
		Path realmConfigFile = Paths
				.get(appBase + File.separator + "realms" + File.separator + realm + "-keycloak.json");
		if (!cache.contains(realm)) {
			try (InputStream is = Files.newInputStream(realmConfigFile)) {
				this.adapterConfig = KeycloakDeploymentBuilder.loadAdapterConfig(is);
				cache.put(realm, KeycloakDeploymentBuilder.build(adapterConfig));
			} catch (IOException e) {
				return keycloakDeployment;
			}
		}

		keycloakDeployment = cache.get(realm);
		return keycloakDeployment;
	}

	public KeycloakDeployment getKeycloakDeployment() {
		return keycloakDeployment;
	}
}
