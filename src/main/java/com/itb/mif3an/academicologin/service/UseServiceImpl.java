package com.itb.mif3an.academicologin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.itb.mif3an.academicologin.model.Role;
import com.itb.mif3an.academicologin.model.User;
import com.itb.mif3an.academicologin.repository.RoleRepository;
import com.itb.mif3an.academicologin.repository.UserRepository;
import com.itb.mif3an.academicologin.web.dto.UserDto;

@Service
public class UseServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User findByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	public User save(UserDto userDto) {

		User user = new User(userDto.getFirstName(),
							userDto.getLastName(),
							userDto.getEmail(), 
							passwordEncoder.encode(userDto.getPassword()),
							new ArrayList<>(), //PARA ENDEREÇOS
				
							new ArrayList<>()); // PARA PAPÉIS

		userRepository.save(user);
		this.addRoleToUser(user.getEmail(), "ROLE_USER");
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
																  user.getPassword(),
																  mapRoleToAuthorities(user.getRoles()));
	}
	
	//MÉTODO RESPONSÁVEL EM TRAZER OS PAPÉIS RELACIONADOS AOS USUARIOS
	
	private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userRepository.findByEmail(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	//MÉTODO RESPONSÁVEL EM BUSCAR O USUÁRIO AUTENTICADO
	@Override
	public User getAuthenticatedUser() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if(principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		}else {
			username = principal.toString();
		}
		
		User user = userRepository.findByEmail(username);
		
		return user;
	}

	@Override
	public User update(UserDto userDto) {
		
		User user = userRepository.findByEmail(userDto.getEmail());
		user.setFirstName(user.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setDataNascimento(userDto.getDataNascimento());
		user.setEnderecos(userDto.getEnderecos());
		
		
		return userRepository.save(user);
	}

}
