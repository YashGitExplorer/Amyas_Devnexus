package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Post;

public interface PostService {

	List<Post> getAllPosts();

	Post getPostById(Long postId);

	void savePost(Post post);

	void deletePost(Post post);

}
