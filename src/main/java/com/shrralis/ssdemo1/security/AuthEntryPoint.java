package com.shrralis.ssdemo1.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 12:59 PM
 */
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException authException
	) throws IOException {
		response.sendError(
				HttpServletResponse.SC_UNAUTHORIZED,
				String.format("Unauthorized + (%s)", authException.getMessage())
		);
	}
}
