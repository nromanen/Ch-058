package com.shrralis.ssdemo1.controller.api.admin.user;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class UserManagementController {

	private final IUserService userService;

	@Autowired
	public UserManagementController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public JsonResponse getAll(@PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable
//			@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int size
	) {
		return new JsonResponse(userService.findAll(pageable).getContent());
	}

//	@GetMapping
//	public JsonResponse getAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
//		return new JsonResponse(userService.findAll(pageable));
//	}

	@GetMapping("/{id}")
	public JsonResponse getById(@PathVariable Integer id) throws AbstractCitizenException {
		return new JsonResponse(userService.findById(id));
	}

	@GetMapping("/login/{login}")
	public JsonResponse getByLogin(@PathVariable String login) throws AbstractCitizenException {
		return new JsonResponse(userService.findByLogin(login));
	}

	@GetMapping("/search/{query}")
	public JsonResponse getByLoginOrEmail(@PathVariable String query, @PageableDefault(page = 0, size = 10, sort = "name") Pageable pageable) {
		return new JsonResponse(userService.findByLoginOrEmail(query, query, pageable).getContent());
	}

	@PutMapping("/{id}/{type}")
	public JsonResponse setStatus(@PathVariable int id, @PathVariable String type) throws AbstractCitizenException {
		return new JsonResponse(userService.setStatus(User.Type.valueOf(type.toUpperCase()), id));
	}

	@GetMapping("/type/{type}")
	public JsonResponse getByStatus(@PathVariable String type,
	                                @RequestParam(required = false, defaultValue = "0") int page,
	                                @RequestParam(required = false, defaultValue = "10") int size) {
		return new JsonResponse(userService.findByType(User.Type.valueOf(type.toUpperCase()), new PageRequest(page, size)));
	}
}
