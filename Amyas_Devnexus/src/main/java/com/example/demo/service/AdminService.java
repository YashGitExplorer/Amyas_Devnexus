package com.example.demo.service;

import java.util.List;

import com.example.demo.Dto.AdminDto;
import com.example.demo.model.Admin;

public interface AdminService {
	Admin save(AdminDto admindto);
	Admin findByUsername(String username);
	List<Admin> getAllAdmins();
	void delete(Admin admin);
	Admin updateAdminDetails(String username, String name, String email);
}
