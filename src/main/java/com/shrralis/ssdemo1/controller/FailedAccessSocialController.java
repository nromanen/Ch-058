package com.shrralis.ssdemo1.controller;

import com.shrralis.tools.model.JsonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class FailedAccessSocialController {
	@GetMapping(name = "/signin", params = {"error", "error_description"})
	public void redirect(HttpServletResponse response) throws IOException {
		response.sendRedirect("http://localhost:8081/#/auth");
	}
}
