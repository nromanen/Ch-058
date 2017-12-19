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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/19/17 at 7:51 PM
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${security.jwt.resource-ids}")
    private String resourceIds;
    private ResourceServerTokenServices tokenServices;

    @Autowired
    public void setTokenServices(ResourceServerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**", "/api-docs/**").permitAll()
                .antMatchers("/springjwt/**").authenticated();
    }
}
