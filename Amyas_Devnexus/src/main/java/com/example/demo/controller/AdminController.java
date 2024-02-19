package com.example.demo.controller;

import java.util.List;

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
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	@GetMapping("/")
	public String homes() {
		return "home";
	}
	@GetMapping("/index")
    public String index() {
        return "index"; 
    }
	@GetMapping("/updateadmin")
	public String updateeadmin() {
		return "updateadmin";
	}
	@GetMapping("/viewalladmin")
	public String viewAllAdmin(Model model) {
	    List<Admin> admins = adminService.getAllAdmins();
	    model.addAttribute("admins", admins);
	    return "viewalladmin";
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
    	 Admin existingAdmin = adminService.findByUsername(adminDto.getUsername());
    	    if (existingAdmin != null) {
    	        // Admin with the same username already exists, add an error message
    	        model.addAttribute("error", "Admin with the same username already exists");
    	        return "adminregistraction"; // Return to the registration page with an error message
    	    }
        adminService.save(adminDto);
        model.addAttribute("success", true);
        return "redirect:/index"; // Redirect to the index page after registration
    }
//    @PostMapping("/updateadmindetails")
//    public String updateadmin(@ModelAttribute AdminDto admindto) {
//    
//    }
   

}
