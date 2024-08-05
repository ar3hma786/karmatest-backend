package com.karmatechnologiestest.karma.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
	
	private String jwt;
	
	private String message;
	
	

}
