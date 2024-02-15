package com.example.demo.service;

import com.example.demo.Dto.AdminDto;
import com.example.demo.model.Admin;

public interface AdminService {
	Admin save(AdminDto admindto);
	Admin findByUsername(String username);

}
