package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UserConnection {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    private User user;

	    @ManyToOne
	    private User connectedUser;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public User getConnectedUser() {
			return connectedUser;
		}

		public void setConnectedUser(User connectedUser) {
			this.connectedUser = connectedUser;
		}
}
