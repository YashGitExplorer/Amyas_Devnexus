package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dto.AdminDto;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;






@Controller

public class AdminController {
	
	@Autowired
	AdminService adminService;
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/index")
    public String index() {
        return "index"; 
    }
	@GetMapping("/adminregistraction")
	public String adminregistration() {
		return "adminregistraction";
	}
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Admin admin = adminService.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return "redirect:/index"; 
        } else {
        	return "redirect:/?error=true"; 
        }
    }
    @PostMapping("/register")
    public String register(@ModelAttribute AdminDto adminDto,Model model) {
        adminService.save(adminDto);
        model.addAttribute("success", true);
        return "redirect:/index"; // Redirect to the index page after registration
    }


}