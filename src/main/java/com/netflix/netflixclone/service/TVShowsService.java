package com.netflix.netflixclone.service;

import java.util.List;

import com.netflix.netflixclone.dto.TVShowsRequest;

import com.netflix.netflixclone.entities.TVShows;
import com.netflix.netflixclone.exception.TVShowsException;

public interface TVShowsService {
  
	public TVShows registerRequest(TVShowsRequest request) throws TVShowsException;
	
	public TVShows updateRequest(TVShowsRequest request, Long tvshowsId) throws  TVShowsException;
	
	public TVShows findById(Long tvshowId) throws TVShowsException;
	
	public TVShows deleteById(Long tvshowId) throws TVShowsException;
	
	public TVShows searchByTitle(String tvShowName) throws TVShowsException;
	
	public Iterable<TVShows> findAll();
	
	public List<TVShows> searchByGenre(String genre) throws TVShowsException;
	
}
