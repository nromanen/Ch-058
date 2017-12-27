package com.shrralis.ssdemo1.security;

import com.shrralis.ssdemo1.controller.AuthRestController;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/27/17 at 12:49 AM
 */
@Component
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
		implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
	@Autowired
	private AuthRestController authRestController;

	@Override
	public void onLogoutSuccess(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		new ObjectMapper().writeValue(httpServletResponse.getWriter(), authRestController.checkCurrSession(null));
		httpServletResponse.setStatus(200);
	}
}
