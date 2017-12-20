package com.shrralis.ssdemo1.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrralis.ssdemo1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 1:00 PM
 */
@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private final ObjectMapper mapper;

	@Autowired
	public AuthSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
	) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);

		RestUserDetails userDetails = (RestUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();

		userDetails.setUser(user);

		PrintWriter writer = response.getWriter();

		mapper.writeValue(writer, user);
		writer.flush();
	}
}
