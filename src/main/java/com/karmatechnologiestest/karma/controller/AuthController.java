package com.karmatechnologiestest.karma.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karmatechnologiestest.karma.config.JwtProvider;
import com.karmatechnologiestest.karma.entities.User;
import com.karmatechnologiestest.karma.enums.ROLE;
import com.karmatechnologiestest.karma.exception.UserException;
import com.karmatechnologiestest.karma.repository.UserRepository;
import com.karmatechnologiestest.karma.request.LoginRequest;
import com.karmatechnologiestest.karma.response.AuthResponse;
import com.karmatechnologiestest.karma.service.CustomUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
   
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtProvider jwtTokenProvider;
	private CustomUserDetailsService customUserDetails;
	

	public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtTokenProvider,
			CustomUserDetailsService customUserDetails) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.customUserDetails = customUserDetails;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException{
		
		  	String username = user.getUsername();
	        String password = user.getPassword();
	        String firstName=user.getFirstName();
	        String lastName=user.getLastName();;
	        LocalDateTime createdAt = LocalDateTime.now();
	        System.out.println(createdAt);
	        ROLE role=user.getRole();
	        
	        User isEmailExist=userRepository.findByUsername(username);

	       
	        if (isEmailExist!=null) {
	        	
	            throw new UserException("Email Is Already Used With Another Account");
	        }

	        // Create new user
			User createdUser= new User();
			createdUser.setFirstName(firstName);
			createdUser.setLastName(lastName);
			createdUser.setUsername(username);
	        createdUser.setPassword(passwordEncoder.encode(password));
	        createdUser.setDatetime(createdAt);
	        createdUser.setRole(role);
	        
	        User savedUser= userRepository.save(createdUser);
	        System.out.println(savedUser);

	        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        String jwt = jwtTokenProvider.generateToken(authentication);

	        AuthResponse authResponse = new AuthResponse();
	        
	        authResponse.setJwt(jwt);
			authResponse.setMessage("Register Success");
			
	        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
	}
	
	@PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        
        System.out.println(username +" ----- "+password);
        
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        
        String jwt = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse= new AuthResponse();
		
        authResponse.setJwt(jwt);
		authResponse.setMessage("Login Success");
		
		
        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
    }
	
	private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        
        System.out.println("sign in userDetails - "+userDetails);
        
        if (userDetails == null) {
        	System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        	System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}