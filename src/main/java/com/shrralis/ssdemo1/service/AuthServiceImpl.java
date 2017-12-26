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

package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.dto.RegisterUserDTO;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.BadParameterFormatException;
import com.shrralis.ssdemo1.exception.EntityNotUniqueException;
import com.shrralis.ssdemo1.exception.interfaces.AbstractShrralisException;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.service.interfaces.IAuthService;
import com.shrralis.tools.TextUtils;
import com.shrralis.tools.model.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 5:26 PM
 */
@Service
public class AuthServiceImpl implements IAuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	private static final String LOGIN_PATTERN = "^[A-Za-z_\\-.0-9]+$";
	@Resource
	private UsersRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JsonResponse signUp(RegisterUserDTO user) throws AbstractShrralisException {
	    if (user == null) {
		    throw new NullPointerException("Passed `User` is null");
	    }

	    if (repository.getByLogin(user.getLogin()) != null) {
		    throw new EntityNotUniqueException(EntityNotUniqueException.Entity.USER, "login");
	    }

	    if (repository.getByEmail(user.getEmail()) != null) {
		    throw new EntityNotUniqueException(EntityNotUniqueException.Entity.USER, "email");
	    }

	    if (!isLoginValid(user.getLogin())) {
		    throw new BadParameterFormatException("login");
	    }

	    if (!TextUtils.isEmailValid(user.getEmail())) {
		    throw new BadParameterFormatException("email");
	    }

	    if (!isPasswordValid(user.getPassword())) {
		    throw new BadParameterFormatException("password");
	    }

	    if (!TextUtils.isNameValid(user.getName())
			    || !TextUtils.isNameValid(user.getSurname())) {
		    throw new BadParameterFormatException("name OR surname", false);
	    }
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    repository.save(User.Builder.anUser()
			    .setLogin(user.getLogin())
			    .setEmail(user.getEmail())
			    .setPassword(user.getPassword())
			    .setName(user.getName())
			    .setSurname(user.getSurname())
			    .build());
	    return new JsonResponse(JsonResponse.OK);
    }

	private boolean isLoginValid(String login) {
		return !TextUtils.isEmpty(login)
				&& login.length() >= User.MIN_LOGIN_LENGTH && login.length() <= User.MAX_LOGIN_LENGTH
				&& login.matches(LOGIN_PATTERN);
	}

	private boolean isPasswordValid(String password) {
		return !TextUtils.isEmpty(password)
				&& password.length() >= User.MIN_PASSWORD_LENGTH
				&& password.length() <= User.MAX_PASSWORD_LENGTH;
	}
}
