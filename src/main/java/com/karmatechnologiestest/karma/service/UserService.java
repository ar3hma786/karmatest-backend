package com.karmatechnologiestest.karma.service;

import java.util.List;

import com.karmatechnologiestest.karma.entities.User;
import com.karmatechnologiestest.karma.exception.UserException;



public interface UserService  {
  
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public List<User> findAllUsers();

	User findUserByUsername(String username) throws UserException;
}
