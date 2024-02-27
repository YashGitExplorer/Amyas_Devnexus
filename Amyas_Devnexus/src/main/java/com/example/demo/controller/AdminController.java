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

import jakarta.servlet.http.HttpSession;

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
    public String index(Model model,HttpSession session) {
		String username=(String) session.getAttribute("adminusername");
		Admin admin=adminService.findByUsername(username);
		model.addAttribute("admin",admin);
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
	@GetMapping("/deletadminform")
	public String deleteadminform() {
		return "deletadminform";
	}
	
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model,HttpSession session) {
        Admin admin = adminService.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
        	session.setAttribute("adminusername", username);
        	session.setAttribute("adminpassword", password);
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


    @PostMapping("/deletadmin")
    public String deleteAdmin(@RequestParam String username, @RequestParam String password, Model model) {
        Admin admin = adminService.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            adminService.delete(admin); 
            return "redirect:/index"; 
        } else {
            return "redirect:/deletadminform?error=true"; 
        }
    }
    @PostMapping("/updateadmindetails")
    public String updateAdminDetails(HttpSession session, @RequestParam String name, @RequestParam String email, Model model) {
        String username = (String) session.getAttribute("adminusername");
        if (username != null) {
            Admin updatedAdmin = adminService.updateAdminDetails(username, name, email);
            
            if (updatedAdmin != null) {
                model.addAttribute("success", "Admin details updated successfully");
            } else {
                model.addAttribute("error", "Failed to update admin details");
            }
            return "redirect:/index"; // Redirect to the update admin page
        } else {
            model.addAttribute("error", "Session expired or unauthorized access");
            return "redirect:/login"; // Redirect to the login page
        }
    }
   
}
