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

package com.shrralis.ssdemo1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrralis.ssdemo1.service.interfaces.IAuthService;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 3:15 PM
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);
	private static final String POST_SUCCESS_AUTH_URL = "/auth/login";

	private RedirectStrategy redirectStrategy;
	private IAuthService authService;
	private AuthenticationTrustResolver authTrustResolver;

	@Autowired
	public AuthSuccessHandler(IAuthService authService,
	                          AuthenticationTrustResolver authTrustResolver) {
		this.authService = authService;
		this.authTrustResolver = authTrustResolver;
		redirectStrategy = new DefaultRedirectStrategy();
	}

	@Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
	    String targetUrl = determineTargetUrl();

        if (response.isCommitted()) {
	        logger.info("Response has already been committed. Unable to redirect to {}", targetUrl);
	        return;
        }
	    new ObjectMapper().writeValue(response.getWriter(), new JsonResponse(
			    authService.getCurrentSession(SecurityContextHolder.getContext().getAuthentication(),
					    authTrustResolver)
	    ));
	    response.setStatus(HttpServletResponse.SC_OK);
    }

	protected String determineTargetUrl() {
		return POST_SUCCESS_AUTH_URL;
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
