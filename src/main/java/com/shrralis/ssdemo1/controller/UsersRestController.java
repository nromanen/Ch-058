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

import com.shrralis.ssdemo1.service.UserService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsersRestController {
    @Autowired
    private UserService service;

    @RequestMapping(
			value = "test",
			method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = "application/json")
	public JsonResponse sayHelloWorld() {
		return new JsonResponse("Hello world!");
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("getAll")
	public JsonResponse getAllUsers() {
		return service.getAllUsers();
	}
}
