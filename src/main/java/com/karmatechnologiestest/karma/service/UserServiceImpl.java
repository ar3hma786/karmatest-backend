package com.karmatechnologiestest.karma.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.karmatechnologiestest.karma.config.JwtProvider;
import com.karmatechnologiestest.karma.entities.User;
import com.karmatechnologiestest.karma.exception.UserException;
import com.karmatechnologiestest.karma.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;

	public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
		super();
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
        
		Optional<User> user=userRepository.findById(userId);
		
		if(user.isPresent()){
			return user.get();
		}
		throw new UserException("user not found with id "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
       
		String username=jwtProvider.getTokenFromJwt(jwt);
		
		System.out.println("username"+username);
		
		User user=userRepository.findByUsername(username);
		
		if(user==null) {
			throw new UserException("user not exist with email "+username);
		}
		System.out.println("email user"+user.getUsername());
		return user;
	}

	@Override
	public User findUserByUsername(String username) throws UserException {
        User user=userRepository.findByUsername(username);
		
		if(user!=null) {
			
			return user;
		}
		
		throw new UserException("user not exist with username "+username);
	}

	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}

}
