package com.example.demo.ServiceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.AdminDto;
import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminRepository adminRepository;

	@Override
	  public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setName(adminDto.getName());
        admin.setEmail(adminDto.getEmail());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        return adminRepository.save(admin);
    }
	@Override
	public Admin findByUsername(String username) {
		// TODO Auto-generated method stub
		return adminRepository.findByUsername(username);
	}
	 @Override
	    public List<Admin> getAllAdmins() {
	        return adminRepository.findAll();
	    }
	@Override
	public void delete(Admin admin) {
		adminRepository.delete(admin);
	}
	@Override
	public Admin updateAdminDetails(String username, String name, String email) {
		 Admin admin = adminRepository.findByUsername(username);
	        if (admin != null) {
	            admin.setName(name);
	            admin.setEmail(email);
	            return adminRepository.save(admin);
	        }
	        return null; 
	}
	 

}
