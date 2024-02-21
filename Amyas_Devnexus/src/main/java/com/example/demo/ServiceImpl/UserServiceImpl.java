package com.example.demo.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.UserDto;
import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service

public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

	@Override
	public User save(UserDto userdto) {
		 User user = new User();
	        user.setFirstname(userdto.getFirstname());
	        user.setLastname(userdto.getLastname());
	        user.setPhonenumber(userdto.getPhonenumber());
	        user.setEmail(userdto.getEmail());
	        user.setUsername(userdto.getUsername());
	        user.setPassword(userdto.getPassword());
		
		return userRepository.save(user);
	}

}
