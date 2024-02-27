package com.example.demo.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.UserDto;
import com.example.demo.model.Admin;
import com.example.demo.model.Post;
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

	@Override
	public User findbyId(long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
		
	}

	@Override
	public List<User> getAlluser() {
		
		return userRepository.findAll();
	}

	@Override
	public User updateUserDetails(String username, UserDto userDto) {
		User existingUser = findByUsername(username);
        if (existingUser != null) {
            existingUser.setFirstname(userDto.getFirstname());
            existingUser.setLastname(userDto.getLastname());
            existingUser.setBio(userDto.getBio());
            existingUser.setDate_of_birth(userDto.getDate_of_birth());
            return userRepository.save(existingUser);
        }
        return null;
	}

	@Override
	public boolean updatePassword(String username, String useroldPassword, String password) {
		User user = findByUsername(username);
        if (user != null && user.getPassword().equals(useroldPassword)) {
            user.setPassword(password); 
            userRepository.save(user); 
            return true;
        }
        return false;
	}

	
	

	
}
