package com.example.dsp.api.action.ping;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class PingController {

	private static final String SUCCESS = "pong";

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = { "/ping" })
	@ResponseBody
	public String ping(HttpServletResponse response) throws Exception {
		return SUCCESS;
	}
}
