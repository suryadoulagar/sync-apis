package com.sync.backend.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author surya
 *
 */
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	private int userId;

	@NotEmpty(message = "name can not be empty")
	@Size(min = 4, message = "username should be min of 4 characters")
	private String name;

	@NotEmpty
	@Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "email is not valid!")
	private String email;

//	Minimum eight characters, at least one letter and one number:
	@NotEmpty
	@Size(min = 8, message = "password must be Minimum eight characters, at least one letter and one number")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
	private String password;

	@NotEmpty
	private String description;

}
