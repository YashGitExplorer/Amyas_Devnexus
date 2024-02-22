package com.example.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	PostRepository postRepository;
	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}
	

}
