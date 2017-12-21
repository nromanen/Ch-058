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

package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.dto.RegisterUserDTO;
import com.shrralis.ssdemo1.service.interfaces.IAuthService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/20/17 at 5:59 PM
 */
@RestController
@RequestMapping("/auth")
public class AuthRestController {
    @Autowired
    IAuthService service;
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    /*@RequestMapping("/login")
    ResponseEntity<Boolean> isCurrentUserLoggedIn() {
        return new ResponseEntity<>(Boolean.FALSE, OK);
    }*/

    @RequestMapping(
            value = "signUp",
            consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST)
    public JsonResponse signUp(@RequestBody RegisterUserDTO user) {
        return service.signUp(user);
    }

    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authenticationTrustResolver.isAnonymous(authentication);
    }

    private String getRedirectPath() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<String> authorities = new HashSet<>();

        for (GrantedAuthority authority : principal.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }

        if (authorities.contains("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else if (authorities.contains("ROLE_LEADER")) {
            return "redirect:/leader";
        } else {
            return "redirect:/user/init";
        }
    }
}
