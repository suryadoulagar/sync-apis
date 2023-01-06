package com.sync.backend.payloads;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostImageDto {

	@NotEmpty
	@Size(min = 5, max = 50, message = "title should be in between 5 to 10 characters")
	private String title;

	private Integer postId;

	@NotEmpty
	@Size(max = 9999, message = "content can not exceed 9999 characters")
	private String content;

	private String imageName;

	private Date addedDate;

	private UserDto user;

}
