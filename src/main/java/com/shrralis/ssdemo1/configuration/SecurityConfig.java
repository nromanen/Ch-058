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

import com.shrralis.ssdemo1.security.RESTAuthenticationEntryPoint;
import com.shrralis.ssdemo1.security.RESTAuthenticationFailureHandler;
import com.shrralis.ssdemo1.security.RESTAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.signing-key}")
    private String signingKey;
    @Value("${security.encoding-strength}")
    private Integer encodingStrength;
    @Value("${security.security-realm}")
    private String securityRealm;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    private RESTAuthenticationEntryPoint authenticationEntryPoint;
    private RESTAuthenticationFailureHandler authenticationFailureHandler;
    private RESTAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void setAuthenticationEntryPoint(RESTAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Autowired
    public void setAuthenticationFailureHandler(RESTAuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Autowired
    public void setAuthenticationSuccessHandler(RESTAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .realmName(securityRealm)
                .and()
                .csrf().disable();
        /*http.csrf().disable().
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/signUp").permitAll()
                .antMatchers("/admin/**").hasRole(User.Type.ADMIN.name())
                .anyRequest().authenticated()
                .and()
                *//*.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)*//*
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin().successHandler(authenticationSuccessHandler)
                .and()
                .formLogin().failureHandler(authenticationFailureHandler);*/
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(encodingStrength));
        // TODO: remove this after testing
        // Create a default account
        /*auth.inMemoryAuthentication()
                .withUser("admin")
                .password("password")
                .roles("ADMIN");*/
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    //Making this primary to avoid any accidental duplication with another token service instance of the same name
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
