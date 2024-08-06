package com.karmatechnologiestest.karma.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(AdminException.class)
	public ResponseEntity<ErrorDetails> handleUserException(AdminException userException, WebRequest request){
		ErrorDetails errorDetails = new ErrorDetails(userException.getMessage(), request.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
