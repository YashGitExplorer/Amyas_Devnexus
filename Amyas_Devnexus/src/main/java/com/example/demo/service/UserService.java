package com.example.demo.service;


import com.example.demo.Dto.UserDto;
import com.example.demo.model.User;

public interface UserService {

	User findByUsername(String username);

	User save(UserDto userdto);

	User findbyId(long userId);

//	User getCurrentUser();

}
