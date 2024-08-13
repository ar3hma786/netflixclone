package com.netflix.netflixclone.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.netflix.netflixclone.config.JwtProvider;
import com.netflix.netflixclone.entities.User;
import com.netflix.netflixclone.exception.UserException;
import com.netflix.netflixclone.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;

	public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
		super();
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
        
		Optional<User> user=userRepository.findById(userId);
		
		if(user.isPresent()){
			return user.get();
		}
		throw new UserException("user not found with id "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
       
		String email=jwtProvider.getTokenFromJwt(jwt);
		
		System.out.println("email"+email);
		
		User user=userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not exist with email "+email);
		}
		System.out.println("email user"+user.getEmail());
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws UserException {
        User user=userRepository.findByEmail(email);
		
		if(user!=null) {
			
			return user;
		}
		
		throw new UserException("user not exist with username "+email);
	}

	@Override
	public List<User> findAllUsers() {
		
		return userRepository.findAll();
	}

}
