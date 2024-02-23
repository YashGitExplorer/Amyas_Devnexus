package com.example.demo.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	PostRepository postRepository;
	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}
	@Override
	public Post getPostById(Long postId) {
		Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.orElse(null);
	}
	@Override
	 @Transactional
	public void savePost(Post post) {
		postRepository.save(post);
		
	}
	

}
