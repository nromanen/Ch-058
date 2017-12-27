package com.shrralis.ssdemo1.controller.system;

import com.shrralis.tools.model.JsonError;
import com.shrralis.tools.model.JsonResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/23/17 at 9:06 PM
 */
@RestController
public class GeneralRestController {
	@RequestMapping("accessDenied")
	public JsonResponse accessDenied() {
		return new JsonResponse(JsonError.Error.ACCESS_DENIED);
	}
}
