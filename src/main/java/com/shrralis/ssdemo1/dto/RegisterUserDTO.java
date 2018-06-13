/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.dto;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.tools.model.JsonError;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO that is received and contains information
 * that is necessary for creating new account
 *
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 5:38 PM
 */
public class RegisterUserDTO implements Serializable {

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Pattern(regexp = User.LOGIN_PATTERN, message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	@Size(
			min = User.MIN_LOGIN_LENGTH, max = User.MAX_LOGIN_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String login;

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Email(message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	@Size(
			min = User.MIN_EMAIL_LENGTH, max = User.MAX_EMAIL_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String email;

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Size(
			min = User.MIN_PASSWORD_LENGTH, max = User.MAX_PASSWORD_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String password;

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Pattern(regexp = User.NAME_PATTERN, message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	@Size(
			min = User.MIN_NAME_LENGTH, max = User.MAX_NAME_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String name;

	@NotBlank(message = JsonError.Error.MISSING_FIELD_NAME)
	@Pattern(regexp = User.NAME_PATTERN, message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	@Size(
			min = User.MIN_SURNAME_LENGTH, max = User.MAX_SURNAME_LENGTH,
			message = JsonError.Error.BAD_FIELD_FORMAT_NAME)
	private String surname;

	private List<String> type;

    public String getLogin() {
	    return StringUtils.trim(login);
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
	    return StringUtils.trim(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
	    return StringUtils.trim(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
	    return StringUtils.trim(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
	    return StringUtils.trim(surname);
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}
}
