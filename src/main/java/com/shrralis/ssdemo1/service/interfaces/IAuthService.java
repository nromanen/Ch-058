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

import com.shrralis.ssdemo1.dto.RegisterUserDTO;
import com.shrralis.ssdemo1.exception.interfaces.AbstractShrralisException;
import com.shrralis.tools.model.JsonResponse;

/**
 * Interface for managing user accounts
 *
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 5:27 PM
 */
public interface IAuthService {
	/**
	 * Creates new user account and returns error
	 *
	 * @param user
	 * 		{@link com.shrralis.ssdemo1.dto.RegisterUserDTO}
	 *
	 * @return {@link com.shrralis.tools.model.JsonResponse}
	 *
	 * @throws AbstractShrralisException
	 * @see com.shrralis.tools.model.JsonError.Error OR {@value com.shrralis.tools.model.JsonResponse#OK}
	 */
	JsonResponse signUp(RegisterUserDTO user) throws AbstractShrralisException;
}
