package com.netflix.netflixclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netflix.netflixclone.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);

}
