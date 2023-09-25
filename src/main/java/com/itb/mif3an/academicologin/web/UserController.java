package com.itb.mif3an.academicologin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.itb.mif3an.academicologin.model.User;
import com.itb.mif3an.academicologin.service.UserService;
import com.itb.mif3an.academicologin.web.dto.UserDto;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/users/home")
	public String homeUser(Model model) {
		
		String home = "index";
		User user = userService.getAuthenticatedUser();
		String username = user.getEmail();
		model.addAttribute("username", username);
		
		return home;
	}
	
	@GetMapping("/users/perfil/{username}")
	public String showPerfilForm(@PathVariable("username")String username, ModelMap model) {
		
		UserDto userDto = new UserDto();
		userDto.setEmail(username);
		User user = userService.findByEmail(userDto.getEmail());
		model.addAttribute("user", user);
		
		return "update-registration";
	}
}
