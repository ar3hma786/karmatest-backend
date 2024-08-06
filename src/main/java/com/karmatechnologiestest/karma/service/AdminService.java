package com.karmatechnologiestest.karma.service;

import java.util.List;

import com.karmatechnologiestest.karma.entities.Admin;
import com.karmatechnologiestest.karma.exception.AdminException;



public interface AdminService  {
  
	public Admin findUserById(Long userId) throws AdminException;
	
	public Admin findUserProfileByJwt(String jwt) throws AdminException;
	
	public List<Admin> findAllUsers();

	Admin findUserByUsername(String username) throws AdminException;
}
