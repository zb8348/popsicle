package com.pfw.popsicle.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

	@RequestMapping(value ={ "/403"})
	public String http403(Model model) {
		return "error/403";
	}
	
	@RequestMapping(value ={ "/error"})
	public String httpError(Model model) {
		return "error/error";
	}
}
