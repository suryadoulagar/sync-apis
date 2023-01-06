package com.sync.backend.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sync.backend.payloads.PostImageDto;
import com.sync.backend.payloads.PostImageResponse;
import com.sync.backend.payloads.ResponseMessage;
import com.sync.backend.services.FileService;
import com.sync.backend.services.PostImageService;

/**
 * @author surya
 *
 */
@RestController
@RequestMapping("/api")
public class PostImageController {

	@Autowired
	private PostImageService postImageService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	/**
	 * @param postImageDto
	 * @param userId
	 * @return
	 */
	@PostMapping("/user/{userId}/posts")
	public ResponseEntity<PostImageDto> createPost(@Valid @RequestBody PostImageDto postImageDto,
			@PathVariable Integer userId) {
		PostImageDto createPost = this.postImageService.createPost(postImageDto, userId);
		return new ResponseEntity<PostImageDto>(createPost, HttpStatus.CREATED);
	}

	/**
	 * @param postDto
	 * @param postId
	 * @return
	 */
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostImageDto> updatePost(@RequestBody PostImageDto postDto, @PathVariable Integer postId) {
		PostImageDto updatePost = this.postImageService.updatePost(postDto, postId);

		return new ResponseEntity<PostImageDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @param postId
	 * @return
	 */
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostImageDto> getPostsById(@PathVariable Integer postId) {
		PostImageDto postById = this.postImageService.getPostById(postId);
		return new ResponseEntity<PostImageDto>(postById, HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostImageDto>> getPostsByUser(@PathVariable Integer userId) {
		List<PostImageDto> posts = this.postImageService.getPostByUser(userId);
		return new ResponseEntity<List<PostImageDto>>(posts, HttpStatus.OK);
	}

	/**
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/posts")
	public ResponseEntity<PostImageResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
		PostImageResponse postResponse = this.postImageService.getAllPosts(pageNumber, pageSize);
		return new ResponseEntity<PostImageResponse>(postResponse, HttpStatus.OK);
	}

	/**
	 * @param postId
	 * @return
	 */
	@DeleteMapping("/posts/{postId}")
	public ResponseMessage deletePost(@PathVariable Integer postId) {
		this.postImageService.deletePost(postId);
		return new ResponseMessage("Post is successfully deleted!", true);
	}

	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostImageDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {

		String fileName = this.fileService.uploadImage(path, image);

		PostImageDto postImageDto = this.postImageService.getPostById(postId);
		postImageDto.setImageName(fileName);
		PostImageDto updatePost = this.postImageService.updatePost(postImageDto, postId);

		return new ResponseEntity<PostImageDto>(updatePost, HttpStatus.OK);

	}

	@GetMapping("post/image/{imageName}")
	public void downloadimage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {

		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}
}