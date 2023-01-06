package com.sync.backend.services;

import java.util.List;

import com.sync.backend.payloads.PostImageDto;
import com.sync.backend.payloads.PostImageResponse;

public interface PostImageService {

	PostImageDto createPost(PostImageDto postImageDto, Integer userId);

	PostImageDto updatePost(PostImageDto postImageDto, Integer postId);

	void deletePost(Integer postId);

	PostImageResponse getAllPosts(Integer pageNumber, Integer pageSize);

	PostImageDto getPostById(Integer postId);

	List<PostImageDto> getPostByUser(Integer userId);
}
