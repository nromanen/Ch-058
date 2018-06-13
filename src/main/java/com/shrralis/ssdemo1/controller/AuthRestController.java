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

package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.dto.PasswordRecoveryDTO;
import com.shrralis.ssdemo1.dto.RegisterUserDTO;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.service.interfaces.IAuthService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/20/17 at 5:59 PM
 */
@RestController
@RequestMapping("/auth")
public class AuthRestController {

	private final IAuthService service;

	@Autowired
	public AuthRestController(IAuthService service) {
		this.service = service;
	}

	@GetMapping("/getCurrentSession")
	public JsonResponse getCurrentSession() {
		return new JsonResponse(service.getCurrentSession());
	}

	@PostMapping("/recoverPassword")
	public JsonResponse recoverPassword(@RequestBody @Valid PasswordRecoveryDTO dto) throws AbstractCitizenException {
		return new JsonResponse(service.recoverPassword(dto));
	}

	@PostMapping("/signUp")
	public JsonResponse signUp(@RequestBody @Valid RegisterUserDTO user) throws AbstractCitizenException {
		return new JsonResponse(service.signUp(user));
    }

    @PostMapping("/update")
	public JsonResponse updateUser(@RequestBody @Valid RegisterUserDTO user) {
		return new JsonResponse(service.update(user));
	}


}
