package com.shrralis.ssdemo1.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/29/17 at 5:09 PM
 */
@Component
public class CitizenAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CitizenAccessDeniedHandler.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void handle(
			HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException e
	) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth instanceof AnonymousAuthenticationToken) {
			LOGGER.debug("User: " + auth.getName()
					+ " attempted to access the protected URL: "
					+ request.getRequestURI());
		}
		MAPPER.writeValue(response.getWriter(), new JsonResponse(JsonError.Error.ACCESS_DENIED));
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}
}
