package com.softserveinc.geocitizen.controller.api.admin;

import com.softserveinc.geocitizen.service.interfaces.IMsgService;
import com.softserveinc.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
public class MsgManagementController {

	private final IMsgService msgService;

	@Autowired
	public MsgManagementController(IMsgService msgService) {
		this.msgService = msgService;
	}

	@GetMapping("/fuckoff")
	public JsonResponse getAll() {
		return new JsonResponse(msgService.findAll());
	}
}


