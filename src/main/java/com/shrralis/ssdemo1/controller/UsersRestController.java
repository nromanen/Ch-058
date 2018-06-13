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

import com.shrralis.ssdemo1.service.interfaces.IUserService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

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

	@Secured("ROLE_ADMIN")
	@RequestMapping("/getAll")
	public JsonResponse getAllUsers() {
		return new JsonResponse(service.getAllUsers());
	}

	@RequestMapping("/get/{id}")
	public JsonResponse getUserInfo(@PathVariable int id) {
		return new JsonResponse(service.getUser(id));
	}

	@GetMapping("/currentLang")
	public JsonResponse currentLang(HttpServletRequest request) {
		return new JsonResponse(localeResolver.resolveLocale(request));
	}
}
