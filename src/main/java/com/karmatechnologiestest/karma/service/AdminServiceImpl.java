package com.karmatechnologiestest.karma.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.karmatechnologiestest.karma.config.JwtProvider;
import com.karmatechnologiestest.karma.entities.Admin;
import com.karmatechnologiestest.karma.exception.AdminException;
import com.karmatechnologiestest.karma.repository.AdminRepository;


@Service
public class AdminServiceImpl implements AdminService{
	
	
	private AdminRepository userRepository;
	private JwtProvider jwtProvider;

	public AdminServiceImpl(AdminRepository userRepository, JwtProvider jwtProvider) {
		super();
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public Admin findUserById(Long userId) throws AdminException {
        
		Optional<Admin> user=userRepository.findById(userId);
		
		if(user.isPresent()){
			return user.get();
		}
		throw new AdminException("user not found with id "+userId);
	}

	@Override
	public Admin findUserProfileByJwt(String jwt) throws AdminException {
       
		String username=jwtProvider.getTokenFromJwt(jwt);
		
		System.out.println("username"+username);
		
		Admin user=userRepository.findByUsername(username);
		
		if(user==null) {
			throw new AdminException("user not exist with email "+username);
		}
		System.out.println("email user"+user.getUsername());
		return user;
	}

	@Override
	public Admin findUserByUsername(String username) throws AdminException {
        Admin user=userRepository.findByUsername(username);
		
		if(user!=null) {
			
			return user;
		}
		
		throw new AdminException("user not exist with username "+username);
	}

	@Override
	public List<Admin> findAllUsers() {
		
		return userRepository.findAll();
	}

}
