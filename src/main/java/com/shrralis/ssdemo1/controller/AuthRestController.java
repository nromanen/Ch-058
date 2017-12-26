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
import com.shrralis.ssdemo1.exception.interfaces.AbstractShrralisException;
import com.shrralis.ssdemo1.security.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IAuthService;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/20/17 at 5:59 PM
 */
@RestController
@RequestMapping("auth")
public class AuthRestController {
	private static final Logger logger = LoggerFactory.getLogger(AuthRestController.class);
	@Autowired
	IAuthService service;
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

	@RequestMapping("/login")
	public JsonResponse login(@RequestParam(value = "error", required = false) Boolean isError) {
		if (isError != null) {
			if (isError) {
				return new JsonResponse(JsonError.Error.UNEXPECTED);
			} else {
				return new JsonResponse(JsonError.Error.NO_ERROR);
			}
		}

		if (isCurrentAuthenticationAnonymous()) {
			return new JsonResponse(Map.ofEntries(entry("logged_in", !isCurrentAuthenticationAnonymous())));
		}

		AuthorizedUser authorizedUser = AuthorizedUser.getCurrent();

		return new JsonResponse(Map.ofEntries(
				entry("id", authorizedUser.getId()),
				entry("login", authorizedUser.getUsername()),
				entry("type", authorizedUser.getType())
		));
	}

    @RequestMapping(
            value = "signUp",
		    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
		    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
		    method = RequestMethod.POST)
    public JsonResponse signUp(@RequestBody RegisterUserDTO user) throws AbstractShrralisException {
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
