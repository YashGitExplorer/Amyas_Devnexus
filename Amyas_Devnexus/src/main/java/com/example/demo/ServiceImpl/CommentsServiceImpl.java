package com.example.demo.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comments;
import com.example.demo.repository.CommentsRepository;
import com.example.demo.service.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService {
	@Autowired
	CommentsRepository commentsRepository;

	@Override
	public void save(Comments comment) {
		commentsRepository.save(comment);
		
	}
	

}
