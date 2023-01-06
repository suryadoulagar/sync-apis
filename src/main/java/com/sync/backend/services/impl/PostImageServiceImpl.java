package com.sync.backend.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sync.backend.entities.PostImage;
import com.sync.backend.entities.User;
import com.sync.backend.exceptions.ResourceNotFoundException;
import com.sync.backend.payloads.PostImageDto;
import com.sync.backend.payloads.PostImageResponse;
import com.sync.backend.repositories.PostImageRepo;
import com.sync.backend.repositories.UserRepo;
import com.sync.backend.services.PostImageService;

@Service
public class PostImageServiceImpl implements PostImageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private PostImageRepo postImageRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Override
	public PostImageDto createPost(PostImageDto postDto, Integer userId) {
		
		LOGGER.info("creating post using user id!");

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		PostImage post = this.modelMapper.map(postDto, PostImage.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);

		PostImage newPost = this.postImageRepo.save(post);
		return this.modelMapper.map(newPost, PostImageDto.class);
	}

	@Override
	public List<PostImageDto> getPostByUser(Integer userId) {
		
		LOGGER.info("fetching posts using user id!");

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		List<PostImage> posts = this.postImageRepo.findByUser(user);

		List<PostImageDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostImageDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public PostImageDto getPostById(Integer postId) {
		
		LOGGER.info("fetching posts using post id!");

		PostImage post = this.postImageRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
		return this.modelMapper.map(post, PostImageDto.class);
	}

	@Override
	public PostImageResponse getAllPosts(Integer pageNumber, Integer pageSize) {

		LOGGER.info("fetching all posts!");
		PageRequest p = PageRequest.of(pageNumber, pageSize);

		Page<PostImage> pagePost = this.postImageRepo.findAll(p);

		List<PostImage> allPosts = pagePost.getContent();

		List<PostImageDto> postsDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostImageDto.class))
				.collect(Collectors.toList());
		
		PostImageResponse postResponse = new PostImageResponse();
		
		postResponse.setContent(postsDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostImageDto updatePost(PostImageDto postDto, Integer postId) {
		
		LOGGER.info("updating posts using post id!");

		PostImage post = this.postImageRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		PostImage updatedPost = this.postImageRepo.save(post);

		return this.modelMapper.map(updatedPost, PostImageDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		LOGGER.info("deleting posts using post id!");

		PostImage postImage = this.postImageRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

		this.postImageRepo.delete(postImage);

	}

}
