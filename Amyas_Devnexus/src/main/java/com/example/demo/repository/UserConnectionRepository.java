package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
import com.example.demo.model.UserConnection;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection,Long>{
	

	int countByUser(User currentUser);

	boolean existsByUserAndConnectedUser(User user, User connectedUser);

}
