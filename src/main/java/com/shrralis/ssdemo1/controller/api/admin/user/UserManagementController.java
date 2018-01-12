package com.shrralis.ssdemo1.controller.api.admin.user;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@Secured({"ROLE_ADMIN"})
@RequestMapping("/admin/users")
public class UserManagementController {

	@Autowired
    private IUserService userService;

	@GetMapping({"/{id}"})
	public JsonResponse getById(@PathVariable Integer id) {
        return new JsonResponse(userService.findById(id));
    }

	@GetMapping({"/login/{l}"})
	public JsonResponse getByLogin(@PathVariable String l) {
		return new JsonResponse(userService.findByLogin(l));
	}

	@GetMapping({"/search/{q}"})
	public JsonResponse getByLoginOrEmail(@PathVariable String q) {
		return new JsonResponse(userService.findByLoginOrEmailContainingAllIgnoreCase(q, q));
	}

	@PutMapping({"/{id}/{t}"})
	public JsonResponse setStatus(@PathVariable Integer id, @PathVariable String t) {
		return new JsonResponse(userService.setStatus(User.Type.valueOf(t.toUpperCase()), id));
	}

	@GetMapping({"/type/{t}"})
	public JsonResponse getByStatus(@PathVariable String t) {
		return new JsonResponse(userService.findByType(User.Type.valueOf(t.toUpperCase())));
	}
}
