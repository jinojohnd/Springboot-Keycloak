package com.example.app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Component
public class ThymeleafWebMvcConfig implements ApplicationContextAware, WebMvcConfigurer {

	private ApplicationContext applicationContext;
	
	@Value("${spring.thymeleaf.cache}")
	private Boolean isCached;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setContentType("text/html; charset=UTF-8");
		return viewResolver;
	}

	@Bean
	public ISpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new SpringSecurityDialect());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}

	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(isCached);
		templateResolver.setApplicationContext(this.applicationContext);
		return templateResolver;
	}

}
