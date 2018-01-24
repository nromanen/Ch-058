package com.shrralis.ssdemo1.controller.api.admin.user;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class UserManagementController {

	private final IUserService userService;

	@Autowired
	public UserManagementController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public JsonResponse getById(@PathVariable Integer id) throws AbstractCitizenException {
		return new JsonResponse(userService.findById(id));
	}

	@GetMapping("/login/{login}")
	public JsonResponse getByLogin(@PathVariable String login) throws AbstractCitizenException {
		return new JsonResponse(userService.findByLogin(login));
	}

	@GetMapping("/search/{query}")
	public JsonResponse getByLoginOrEmail(@PathVariable String q) {
		return new JsonResponse(userService.findByLoginOrEmailContaining(q, q));
	}

	@PutMapping("/{id}/{type}")
	public JsonResponse setStatus(@PathVariable Integer id, @PathVariable String type) throws AbstractCitizenException {
		return new JsonResponse(userService.setStatus(User.Type.valueOf(type.toUpperCase()), id));
	}

	@GetMapping("/type/{type}")
	public JsonResponse getByStatus(@PathVariable String type) {
		return new JsonResponse(userService.findByType(User.Type.valueOf(type.toUpperCase())));
	}
}
