package com.example.demo.service;

import com.example.demo.model.User;

public interface UserConnectionService {

	int countConnectionsByUser(User currentUser);

	boolean connectUsers(User currentUser, User connectedUser);

}
