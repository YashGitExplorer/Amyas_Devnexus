package com.example.demo.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
//	@Autowired
//	UserService userService;
	@GetMapping("/")
	public String home() {
		return "userhome";
	}

}
