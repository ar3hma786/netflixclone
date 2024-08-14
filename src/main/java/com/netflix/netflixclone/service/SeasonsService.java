package com.netflix.netflixclone.service;

import com.netflix.netflixclone.dto.SeasonsRequest;

import com.netflix.netflixclone.entities.Seasons;
import com.netflix.netflixclone.exception.SeasonsException;
import com.netflix.netflixclone.exception.TVShowsException;

public interface SeasonsService {
	
	public Seasons registerSeason(SeasonsRequest request) throws SeasonsException, TVShowsException;
    
    public Seasons updateSeason(Seasons seasons, Long seasonsId) throws SeasonsException, TVShowsException;
    
    public Seasons findSeasonById(Long seasonId) throws TVShowsException, SeasonsException;
    
    public Seasons deleteSeasonById(Long seasonId) throws SeasonsException, TVShowsException;
    
    public Iterable<Seasons> findAllSeasons();
}
