package com.sync.backend.services;

import java.util.List;

import com.sync.backend.exceptions.UserAlreadyExistException;
import com.sync.backend.payloads.UserDto;

/**
 * @author surya
 *
 */
public interface UserService {

	/**
	 * @param user
	 * @return
	 * @throws UserAlreadyExistException 
	 */
	UserDto createUser(UserDto user) throws UserAlreadyExistException;

	/**
	 * @param user
	 * @param userId
	 * @return
	 */
	UserDto updateUser(UserDto user, Integer userId);

	/**
	 * @param userId
	 * @return
	 */
	UserDto getUserById(Integer userId);

	/**
	 * @return
	 */
	List<UserDto> getAllUsers();

	/**
	 * @param userId
	 */
	void deleteUser(Integer userId);

}
