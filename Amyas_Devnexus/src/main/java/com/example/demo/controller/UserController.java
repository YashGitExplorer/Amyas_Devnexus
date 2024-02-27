package com.example.demo.controller;

import java.io.IOException;

import java.util.Base64;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;


import com.example.demo.Dto.UserDto;
import com.example.demo.model.Admin;
import com.example.demo.model.Comments;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;

import com.example.demo.service.CommentsService;
import com.example.demo.service.PostService;
import com.example.demo.service.UserConnectionService;
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
	@Autowired 
     UserConnectionService userConnectionService;
	@GetMapping("/user")
	public String home() {
		return "userhome";
	}
	@GetMapping("/deletepost")
	public String deletepost(Model model,HttpSession session) {
		String username=(String) session.getAttribute("username");
		User user=userService.findByUsername(username);
		model.addAttribute("user",user);
		List<Post> posts = postService.getAllPosts(); 
		posts.forEach(post -> post.setImageBase64(Base64.getEncoder().encodeToString(post.getImage())));
        model.addAttribute("posts", posts); 
		return "deletepost";
		
	}
	@GetMapping("/viewalluser")
	public String viewAlluser(Model model) {
		
	    List<User> user = userService.getAlluser();
	    model.addAttribute("users", user);
	    return "viewalluser";
	}
	@GetMapping("/searchuserform")
	public String searchuserform() {
		return "searchuserform";
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
      
		return "userindex";
	}
	@GetMapping("/profilecontroller")
	public String profilecontroller(Model model,HttpSession session) {
		String username = (String) session.getAttribute("username");
		User currentUser = userService.findByUsername(username);
		 int connectionCount = userConnectionService.countConnectionsByUser(currentUser);
        model.addAttribute("user", currentUser);
        model.addAttribute("connectionCount", connectionCount);
		return "userprofile";
	}
	@GetMapping("/userpostform")
	public String userpostform(Model model,HttpSession session) {
		String username = (String) session.getAttribute("username");
		User currentUser = userService.findByUsername(username);
        model.addAttribute("user", currentUser);
		return "userpostform";
	}
	@GetMapping("/deleteuserform")
	 public String deleteuserform() {
	     return "deleteuserform";
	 }
	 
	 
	 @PostMapping("/deleteuser")
	 public String deleteUser(@RequestParam String username, @RequestParam String password, Model model) {
	     User user = userService.findByUsername(username);
	     if (user != null && user.getPassword().equals(password)) {
	         userService.delete(user); 
	         return "redirect:/index";
	     } else {
	         return "redirect:/deleteuserform?error=true";
	     }
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
	 @PostMapping("/searchuser")
	 public String searchUser(@RequestParam("search") String searchTerm, Model model, HttpSession session) {
	     User foundUser = userService.findByUsername(searchTerm);
	     session.setAttribute("foundUser", foundUser);
	     return "redirect:/userdetails"; 
	 }

@GetMapping("/userdetails")
public String userDetails(HttpSession session, Model model) {
    User foundUser = (User) session.getAttribute("foundUser");
    if (foundUser == null) {    
        return "userNotFound"; 
    }
    model.addAttribute("user", foundUser);
    return "userDetails"; 
}
@PostMapping("/connectWithUser")
public String connectWithUser(@RequestParam("userId") long connectedUserId, HttpSession session) {
    String username = (String) session.getAttribute("username");
    User currentUser = userService.findByUsername(username);
    User connectedUser = userService.findbyId(connectedUserId);

    if (currentUser != null && connectedUser != null) {
        
        boolean success = userConnectionService.connectUsers(currentUser, connectedUser);
        
    } else {
    	System.out.println("Already connected");
    }

    return "redirect:/searchuserform";
}
@PostMapping("/deletepostcontroller")
public String deletePost(@RequestParam("postId") Long postId) {
	Post post = postService.getPostById(postId);
    if (post != null) {
        postService.deletePost(post);
    }
    return "redirect:/deletepost";
}
@PostMapping("/editUserDetails")
public String editUserDetails(@ModelAttribute UserDto userDto, HttpSession session, Model model) {
    String username = (String) session.getAttribute("username");
    if (username != null) {
       
        User updatedUser = userService.updateUserDetails(username, userDto);
        if (updatedUser != null) {
            model.addAttribute("success", "User details updated successfully");
        } else {
            model.addAttribute("error", "Failed to update user details");
        }
       
        return "redirect:/profilecontroller";
    } else {
       
        return "redirect:/login";
    }
}



@PostMapping("/updatePassword")
public String updatePassword(@RequestParam String useroldPassword, @RequestParam String password, HttpSession session, Model model) {
 String username = (String) session.getAttribute("username");
 if (username != null) {
     
     User user = userService.findByUsername(username);
     if (user != null) {
         
         if (user.getPassword().equals(useroldPassword)) {
             
             boolean passwordUpdated = userService.updatePassword(username, useroldPassword, password);
             if (passwordUpdated) {
                 model.addAttribute("success", "Password updated successfully");
             } else {
                 model.addAttribute("error", "Failed to update password");
             }
         } else {
             model.addAttribute("error", "Old password doesn't match");
         }
        
         return "redirect:/user";
     } else {
         
         return "redirect:/login";
     }
 } else {
   
     return "redirect:/login";
 }
}
}
