/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.shrralis.ssdemo1")
@PropertySource("classpath:application.properties")
@Import(value = {
		DatabaseConfig.class,
		SecurityConfig.class,
})
public class AppConfig extends WebMvcConfigurerAdapter {

	private static final String EMAIL_HOST = "email.host";
	private static final String EMAIL_PORT = "email.port";
	private static final String EMAIL_USERNAME = "email.username";
	private static final String EMAIL_PASS = "email.password";

	@Resource
	private Environment env;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:8081")
				.allowedMethods("*")
				.allowCredentials(true);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper;
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(env.getRequiredProperty(EMAIL_HOST));
		mailSender.setPort(Integer.parseInt(env.getRequiredProperty(EMAIL_PORT)));
		mailSender.setUsername(env.getRequiredProperty(EMAIL_USERNAME));
		mailSender.setPassword(env.getRequiredProperty(EMAIL_PASS));

		Properties props = mailSender.getJavaMailProperties();

		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		return mailSender;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
