package com.example.demo.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.Dto.UserDto;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;




@Controller

public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@Autowired 
	PostRepository postRepository;
	@GetMapping("/user")
	public String home() {
		return "userhome";
	}
	@GetMapping("/userregistrationform")
	public String userregister() {
		return "userregistrationform";
	}
	@GetMapping("/userindex")
	public String userindex(Model model,HttpSession session) {
		String username=(String)session.getAttribute("username");
		User user=userService.findByUsername(username);
		model.addAttribute("user",user);
		List<Post> posts = postService.getAllPosts(); // Assuming you have a postService to retrieve posts
		posts.forEach(post -> post.setImageBase64(Base64.getEncoder().encodeToString(post.getImage())));
        model.addAttribute("posts", posts); // Add the list of posts to the model
        
		return "userindex";
	}
	@GetMapping("/profilecontroller")
	public String profilecontroller(Model model,HttpSession session) {
		String username = (String) session.getAttribute("username");
		User currentUser = userService.findByUsername(username);
        model.addAttribute("user", currentUser);
         
		return "userprofile";
	}
	@GetMapping("/userpostform")
	public String userpostform() {
		return "userpostform";
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
	 @PostMapping("/savePost")
	    public String savePost(@RequestParam("title") String title,
	                           @RequestParam("content") String content,
	                           @RequestParam("image") MultipartFile imageFile) throws IOException {

	        byte[] imageData = imageFile.getBytes();

	        Post post = new Post();
	        post.setTitle(title);
	        post.setContent(content);
	        post.setImage(imageData);

	        postRepository.save(post);

	        return "redirect:/userindex";
	    }
}
