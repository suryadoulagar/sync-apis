package com.sync.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sync.backend.entities.PostImage;
import com.sync.backend.entities.User;

public interface PostImageRepo extends JpaRepository<PostImage, Integer> {

	List<PostImage> findByUser(User user);

}
