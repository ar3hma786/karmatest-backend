package com.karmatechnologiestest.karma.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.karmatechnologiestest.karma.entities.User;
import com.karmatechnologiestest.karma.repository.UserRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService {

	    private UserRepository userRepository;
		

		public CustomUserDetailsService(UserRepository userRepository) {
			super();
			this.userRepository = userRepository;
		}


		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
			User user = userRepository.findByUsername(username);
			
			if(user == null) {
				throw new UsernameNotFoundException("user not found with email "+username);
			}
			
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
		}


}
