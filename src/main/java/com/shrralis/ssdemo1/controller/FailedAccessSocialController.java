package com.shrralis.ssdemo1.controller;

import com.shrralis.tools.model.JsonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class FailedAccessSocialController {

	@Value("${front.url}")
	private String frontUrl;

	@GetMapping(value = "/signin", params = {"error"})
	public void redirect(HttpServletResponse response) throws IOException {
		response.sendRedirect(frontUrl + "/auth");
	}
}
