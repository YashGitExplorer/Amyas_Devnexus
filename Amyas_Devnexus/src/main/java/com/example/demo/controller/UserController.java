package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dto.AdminDto;
import com.example.demo.Dto.UserDto;
import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;




@Controller

public class UserController {
	@Autowired
	UserService userService;
	@GetMapping("/user")
	public String home() {
		return "userhome";
	}
	@GetMapping("/userregistrationform")
	public String userregister() {
		return "userregistrationform";
	}
	@GetMapping("/userindex")
	public String userindex() {
		return "userindex";
	}
	@GetMapping("/profilecontroller")
	public String profilecontroller(Model model,HttpSession session) {
		String username = (String) session.getAttribute("username");
		User currentUser = userService.findByUsername(username);
        model.addAttribute("user", currentUser);
         
		return "userprofile";
	}
	 @PostMapping("/userregister")
	    public String register(@ModelAttribute 	UserDto userDto,Model model) {
	    	 User existingUser = userService.findByUsername(userDto.getUsername());
	    	    if (existingUser != null) {
	    	        
	    	        model.addAttribute("error", "User with the same username already exists");
	    	        return "adminregistraction"; 
	    	    }
	        userService.save(userDto);
	        model.addAttribute("success", true);
	        return "redirect:/user"; 
	  }
	 @PostMapping("/userlogin")
	    public String login(@RequestParam String username, @RequestParam String password, Model model,HttpSession session) {
	       User user = userService.findByUsername(username);
	        if (user != null && user.getPassword().equals(password)) {
	        	session.setAttribute("username", username);
	        	session.setAttribute("password", password);
	            return "redirect:/userindex"; 
	        } else {
	        	
	        	return "redirect:/?error=true"; 
	        }
	    }
}
