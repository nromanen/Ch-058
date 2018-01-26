package com.shrralis.ssdemo1.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/22/18 at 2:06 PM
 */
@Component
public class Initializer {

	public Initializer(@Value("${front.url}") String frontUrl) {
		PasswordRecoveryEmailMessage.setFrontUrl(frontUrl);
		SignUpEmailMessage.setFrontUrl(frontUrl);
	}
}
