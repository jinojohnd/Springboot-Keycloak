package com.example.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS).resourceChain(false)
				.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
	}
	
	@Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

	@Bean
	@RequestScope
	public AuthenticationData authenticationData(ServletWebRequest httpRequest) {
		String realm = httpRequest.getHeader("x-realm");
		return new AuthenticationData(realm);
	}
}
