package com.example.demo.service;


import java.util.List;

import com.example.demo.Dto.UserDto;
import com.example.demo.model.User;

public interface UserService {

	User findByUsername(String username);

	User save(UserDto userdto);

	User findbyId(long userId);

	void delete(User user);

	List<User> getAlluser();

	User updateUserDetails(String username, UserDto userDto);

	boolean updatePassword(String username, String useroldPassword, String password);


}
