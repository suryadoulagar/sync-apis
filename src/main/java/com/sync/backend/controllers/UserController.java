package com.sync.backend.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sync.backend.exceptions.UserAlreadyExistException;
import com.sync.backend.payloads.ResponseMessage;
import com.sync.backend.payloads.UserDto;
import com.sync.backend.services.UserService;

/**
 * @author surya
 *
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * POST-API
	 * 
	 * @param userDto
	 * @return
	 * @throws UserAlreadyExistException 
	 */
	@PostMapping("/")
	public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto) throws UserAlreadyExistException {
		System.out.println("inside create user controller");
		UserDto createUserDto = this.userService.createUser(userDto);
		System.out.println("new user created!");
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

	/**
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	/**
	 * @param userDto
	 * @param userId
	 * @return
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

	/**
	 * @param userDto
	 * @param userId
	 * @return
	 */
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}

	/**
	 * @param userId
	 * @return
	 */
	@DeleteMapping("/{userId}")
	public ResponseEntity<ResponseMessage> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ResponseMessage>(new ResponseMessage("User deleted successfully", true),
				HttpStatus.OK);
	}

}
