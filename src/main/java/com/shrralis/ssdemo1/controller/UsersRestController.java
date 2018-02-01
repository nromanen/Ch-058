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
import com.shrralis.ssdemo1.exception.BadFieldFormatException;
import com.shrralis.ssdemo1.exception.IllegalParameterException;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IImageService;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UsersRestController {

	private final IUserService service;
	private final IImageService imageService;
	private final LocaleResolver localeResolver;

	@Autowired
	public UsersRestController(IUserService service,
	                           IImageService imageService,
	                           LocaleResolver localeResolver) {
		this.service = service;
		this.imageService = imageService;
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

	@GetMapping(value = "/image/{userId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] userImage(@PathVariable("userId") int userId) throws IOException {
		return imageService.getUserImageInByte(userId);
	}

	@PutMapping("/image")
	public JsonResponse issue(@RequestParam("image") MultipartFile image)
			throws IllegalParameterException, BadFieldFormatException {
		if (image == null) {
			throw new IllegalParameterException("image");
		}
		service.updateImage(imageService.parseImage(image));
		return new JsonResponse(service.getUser(AuthorizedUser.getCurrent().getId()));
	}
}
