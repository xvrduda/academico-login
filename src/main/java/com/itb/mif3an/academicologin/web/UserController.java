package com.itb.mif3an.academicologin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/home")
	public String homeUser() {
		
		return "index";
	}
}
