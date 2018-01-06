package com.shrralis.ssdemo1.dto;

import com.shrralis.ssdemo1.entity.RecoveryToken;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.tools.model.JsonError;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 1/3/18 at 12:00 AM
 */
public class PasswordRecoveryDTO {

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Pattern(regexp = User.LOGIN_PATTERN, message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	@Size(
			min = User.MIN_LOGIN_LENGTH, max = User.MAX_LOGIN_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String login;

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Size(
			min = RecoveryToken.MIN_TOKEN_LENGTH, max = RecoveryToken.MAX_TOKEN_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String token;

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Size(
			min = User.MIN_PASSWORD_LENGTH, max = User.MAX_PASSWORD_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
