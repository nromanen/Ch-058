package com.shrralis.ssdemo1.security;

import com.shrralis.ssdemo1.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/20/17 at 3:19 PM
 */
@Component
public class LoggedInChecker {
	public User getLoggedInUser() {
		User user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			// principal can be "anonymousUser" (String)
			if (principal instanceof RestUserDetails) {
				RestUserDetails userDetails = (RestUserDetails) principal;
				user = userDetails.getUser();
			}
		}
		return user;
	}
}
