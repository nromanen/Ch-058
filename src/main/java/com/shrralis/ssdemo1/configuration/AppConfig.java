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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Locale;
import java.util.Properties;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.shrralis.ssdemo1",  excludeFilters = {
		@ComponentScan.Filter(type = ASSIGNABLE_TYPE,
				value = {
						TestDatabaseConfig.class
				})
})
@PropertySource("classpath:application.properties")
@Import(value = {
		DatabaseConfig.class,
		SecurityConfig.class,
        WebSocketConfig.class,
		SocialConfig.class
})
public class AppConfig extends WebMvcConfigurerAdapter {

	@Value("${email.host}")
	private String emailHost;

	@Value("${email.port}")
	private int emailPort;

	@Value("${email.username}")
	private String emailUsername;

	@Value("${email.password}")
	private String emailPass;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:8081")
				.allowedMethods("*")
				.allowCredentials(true);
	}

	// The next bean is needed if we define messageSource bean
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(emailHost);
		mailSender.setPort(emailPort);
		mailSender.setUsername(emailUsername);
		mailSender.setPassword(emailPass);

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

	@Bean
	public MappingJackson2HttpMessageConverter jacksonConverter() {
		final ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return new MappingJackson2HttpMessageConverter(mapper);
	}

	@Bean
	public StandardServletMultipartResolver resolver() {
		return new StandardServletMultipartResolver();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

		interceptor.setParamName("lang");
		return interceptor;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();

		localeResolver.setDefaultLocale(Locale.US);
		localeResolver.setCookieName("lang");
		localeResolver.setCookieMaxAge(100000);
		return localeResolver;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasenames("classpath:i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

}
