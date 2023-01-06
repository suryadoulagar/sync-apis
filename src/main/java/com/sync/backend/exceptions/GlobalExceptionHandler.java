package com.sync.backend.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sync.backend.payloads.ResponseMessage;

/**
 * @author surya
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

		String message = ex.getMessage();
		ResponseMessage responseMessage = new ResponseMessage(message, false);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ResponseMessage> userAlreadyExistException(UserAlreadyExistException ex) {

		String message = ex.getMessage();
		ResponseMessage responseMessage = new ResponseMessage(message, false);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {

		Map<String, String> respo = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			respo.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(respo, HttpStatus.BAD_REQUEST);
	}

}
