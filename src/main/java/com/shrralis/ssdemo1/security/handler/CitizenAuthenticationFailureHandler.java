package com.shrralis.ssdemo1.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrralis.ssdemo1.security.exception.interfaces.ICitizenAuthenticationException;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.security.core.AuthenticationException;
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

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    AuthenticationException e
	) throws IOException, ServletException {
		if (e.getClass().isAssignableFrom(ICitizenAuthenticationException.class)) {
			logger.error(e.getClass().getName() + ": {}", e);
			MAPPER.writeValue(response.getWriter(), new JsonResponse(((ICitizenAuthenticationException) e).getError()));
		} else {
			logger.error("AuthenticationException: {}", e);
			MAPPER.writeValue(response.getWriter(), new JsonResponse(new JsonError(e.getMessage())));
		}
	}
}
