package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.dto.EditUserDTO;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import com.shrralis.tools.model.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

	@GetMapping("/profile/{id}")
	public JsonResponse userProfile(@PathVariable int id) {
		return new JsonResponse(service.getUserProfile(id));
	}

	@PutMapping("/edit")
	public JsonResponse edit(@RequestBody @Valid EditUserDTO dto) {
		service.edit(dto);
		return new JsonResponse(service.getUser(AuthorizedUser.getCurrent().getId()));
	}

	@ApiOperation(hidden = true, value = "Return language for user")
	@GetMapping("/currentLang")
	public JsonResponse currentLang(HttpServletRequest request) {
		return new JsonResponse(localeResolver.resolveLocale(request));
	}
}