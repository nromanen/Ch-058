package com.shrralis.ssdemo1.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.security.exception.CitizenBadCredentialsException;
import com.shrralis.ssdemo1.security.exception.EmailNotFoundException;
import com.shrralis.ssdemo1.security.exception.TooManyNonExpiredRecoveryTokensException;
import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
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

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    AuthenticationException e
	) throws IOException, ServletException {
		if (e.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
			logger.error("UsernameNotFoundException: {}", e);
			MAPPER.writeValue(response.getWriter(), new JsonResponse(JsonError.Error.USER_NOT_EXIST.forField("login")));
		} else if (e.getClass().isAssignableFrom(EmailNotFoundException.class)) {
			logger.error("EmailNotFoundException: {}", e);
			MAPPER.writeValue(response.getWriter(), new JsonResponse(JsonError.Error.USER_NOT_EXIST.forField("email")));
		} else if (e.getClass().isAssignableFrom(TooManyNonExpiredRecoveryTokensException.class)) {
			logger.error("TooManyNonExpiredRecoveryTokensException: {}", e);
			MAPPER.writeValue(response.getWriter(), new JsonResponse(JsonError.Error.USER_BLOCKED_BY_MAX_FAILED_AUTH));
		} else if (e.getClass().isAssignableFrom(BadCredentialsException.class)) {
			logger.error("BadCredentialsException: {}", e);
			MAPPER.writeValue(response.getWriter(), new JsonResponse(JsonError.Error.BAD_CREDENTIALS));
		} else if (e.getClass().isAssignableFrom(CitizenBadCredentialsException.class)) {
			logger.error("CitizenBadCredentialsException: {}", e);
			MAPPER.writeValue(response.getWriter(), new JsonResponse(JsonError.Error.BAD_CREDENTIALS
					.forField(((CitizenBadCredentialsException) e).getFailedAttempts() +
							"/" + User.MAX_FAILED_AUTH_VALUE)));
		} else {
			logger.error("AuthenticationException: {}", e);
			MAPPER.writeValue(response.getWriter(), new JsonResponse(new JsonError(e.getMessage())));
		}
	}
}
