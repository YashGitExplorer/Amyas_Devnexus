package com.example.demo.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.model.UserConnection;
import com.example.demo.repository.UserConnectionRepository;
import com.example.demo.service.UserConnectionService;

@Service
public class UserConnectionServiceImpl implements UserConnectionService{
  @Autowired
  UserConnectionRepository userConnectionRepository;

@Override
public int countConnectionsByUser(User currentUser) {
	
	return userConnectionRepository.countByUser(currentUser);
}

@Override
public boolean connectUsers(User user, User connectedUser) {
    
    if (userConnectionRepository.existsByUserAndConnectedUser(user, connectedUser)) {
        return false; 
    }

    // Create a new connection
    UserConnection connection = new UserConnection();
    connection.setUser(user);
    connection.setConnectedUser(connectedUser);

    userConnectionRepository.save(connection);
    
    return true; 
}
}
