package com.itb.mif3an.academicologin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itb.mif3an.academicologin.model.User;
import com.itb.mif3an.academicologin.service.UserService;
import com.itb.mif3an.academicologin.web.dto.UserDto;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public UserDto userdto() {
		return new UserDto();
	}
	
	@GetMapping("/registration")
	public String showRegistrationForm() {
		
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registerUserAccount(@ModelAttribute("user")UserDto userDto) {
		//SALVAR NO BANCO
		
		userService.save(userDto);
		
		return "redirect:/registration?success"; //REDIRECIONA PARA UMA "ROTA"
	}
	
	@ResponseBody
	@RequestMapping(value="/registration/ajax/getEmail/{campo}/{valor}")
	public String getSearchResultAjaxRegister(@PathVariable("campo") String campo,
											  @PathVariable("valor") String valor) {
	
		String msg = "";
		
		UserDto userDto = new UserDto();
		userDto.setEmail(valor);
		User user = userService.findByEmail(userDto.getEmail());
		if(user != null) {
			msg = "Email já existente, escolha um email válido";
		}
		
		return msg;
	}
}
