package com.shrralis.ssdemo1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/3/18 at 3:30 PM
 */
@Component
public class CitizenAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private ObjectMapper mapper;

	@Autowired
	public CitizenAuthenticationFailureHandler(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    AuthenticationException e
	) throws IOException, ServletException {
		if (e.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
			logger.error("UsernameNotFoundException: {}", e);
			mapper.writeValue(response.getWriter(), new JsonResponse(JsonError.Error.USER_NOT_EXIST.forField("login")));
		} else if (e.getClass().isAssignableFrom(BadCredentialsException.class)) {
			mapper.writeValue(response.getWriter(), new JsonResponse(JsonError.Error.BAD_CREDENTIALS));
		} else {
			mapper.writeValue(response.getWriter(), new JsonResponse(new JsonError(e.getMessage())));
		}
	}
}
