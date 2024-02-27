package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="user", uniqueConstraints=@UniqueConstraint(columnNames={"email","phonenumber"}))
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String firstname;
	String lastname;
	String date_of_birth;
	String bio;
	String phonenumber;
	String email;
	String username;
	String password;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	    private List<Post> posts;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserConnection> connections;
	public List<UserConnection> getConnections() {
		return connections;
	}
	public void setConnections(List<UserConnection> connections) {
		this.connections = connections;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
