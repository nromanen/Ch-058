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

import com.shrralis.ssdemo1.dto.EditUserDTO;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersRestController {

	private final IUserService service;
	private final LocaleResolver localeResolver;

	@Autowired
	public UsersRestController(IUserService service, LocaleResolver localeResolver) {
		this.service = service;
		this.localeResolver = localeResolver;
	}

	@GetMapping
	public JsonResponse allUsers() {
		return new JsonResponse(service.getAllUsers());
	}

	@GetMapping("/{id}")
	public JsonResponse user(@PathVariable int id) {
		return new JsonResponse(service.getUser(id));
	}

	@PutMapping("/edit")
	public JsonResponse edit(@RequestBody @Valid EditUserDTO dto) {
		service.edit(dto);
		return new JsonResponse(service.getUser(AuthorizedUser.getCurrent().getId()));
	}

	@GetMapping("/currentLang")
	public JsonResponse currentLang(HttpServletRequest request) {
		return new JsonResponse(localeResolver.resolveLocale(request));
	}
}
