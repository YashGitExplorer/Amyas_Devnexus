package com.example.demo.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.Dto.UserDto;
import com.example.demo.model.Comments;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.CommentsService;
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
	CommentsService commentsService;
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
		List<Post> posts = postService.getAllPosts(); 
		posts.forEach(post -> post.setImageBase64(Base64.getEncoder().encodeToString(post.getImage())));
        model.addAttribute("posts", posts); 
       // List<Comments> comments = commentsService.getAllPosts(); 
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
	public String userpostform(Model model,HttpSession session) {
		String username = (String) session.getAttribute("username");
		User currentUser = userService.findByUsername(username);
        model.addAttribute("user", currentUser);
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
	    public String savePost(@RequestParam("userId") long userId,@RequestParam("title") String title,
	                           @RequestParam("content") String content,
	                           @RequestParam("image") MultipartFile imageFile) throws IOException {

	        byte[] imageData = imageFile.getBytes();
	        User user = userService.findbyId(userId);
	        if(user !=null) {
	        Post post = new Post();
	        post.setUser(user);
	        post.setTitle(title);
	        post.setContent(content);
	        post.setImage(imageData);

	        postRepository.save(post);
	        }

	        return "redirect:/userindex";
	    }
	 
	 @PostMapping("/likePost")
	    public String likePost(@RequestParam("postId") long postId) {
	        Post post = postService.getPostById(postId);
	        if (post != null) {
	            post.setLikes(post.getLikes() + 1);
	            // Save the updated post back to the database
	            postService.savePost(post);
	        }
		 System.out.println(postId);
	        
	        return "redirect:/userindex";
	 }
	 @PostMapping("/commentPost")
	 public String commentPost(@RequestParam("postId") Long postId,
	                           @RequestParam("comment") String commentContent) {
	     Post post = postService.getPostById(postId);
	     if (post != null) {
	         Comments comment = new Comments();
	         comment.setContent(commentContent);
	         comment.setPost(post);
	         commentsService.save(comment);
	     }
	     return "redirect:/userindex";
	 }
	
}
