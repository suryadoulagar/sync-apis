package com.sync.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sync.backend.entities.User;

/**
 * @author surya
 *
 */
public interface UserRepo extends JpaRepository<User, Integer> {

}
