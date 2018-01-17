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

package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.dto.PasswordRecoveryDTO;
import com.shrralis.ssdemo1.dto.RegisterUserDTO;
import com.shrralis.ssdemo1.dto.RegisteredUserDTO;
import com.shrralis.ssdemo1.dto.UserSessionDTO;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;

import javax.mail.MessagingException;

/**
 * Interface for managing user accounts.
 *
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 5:27 PM
 */
public interface IAuthService {
	/**
	 * Creates new "session" for recovering of the users password.
	 *
	 * @param login of the user
	 * @return the users login
	 * @throws AbstractCitizenException
	 */
	String generateRecoveryToken(final String login, final String ip) throws AbstractCitizenException, MessagingException;

	/**
	 * Returns current user's session information.
	 *
	 * @return DTO with the information.
	 */
	UserSessionDTO getCurrentSession();

	/**
	 * Recovers users password with new one via received token.
	 *
	 * @param dto
	 * 		that contains necessary data
	 *
	 * @return DTO with registered user information
	 *
	 * @see com.shrralis.ssdemo1.dto.PasswordRecoveryDTO
	 * @see com.shrralis.ssdemo1.dto.RegisteredUserDTO
	 */
	RegisteredUserDTO recoverPassword(PasswordRecoveryDTO dto) throws AbstractCitizenException;

	/**
	 * Creates new user account and returns error.
	 *
	 * @param user that contains information for registration
	 * @see RegisterUserDTO
	 *
	 * @return DTO with registered user information
	 * @see com.shrralis.tools.model.JsonResponse
	 *
	 * @throws AbstractCitizenException
	 */
	RegisteredUserDTO signUp(final RegisterUserDTO user) throws AbstractCitizenException;

	RegisteredUserDTO update(RegisterUserDTO user);

}
