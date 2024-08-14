package com.netflix.netflixclone.service;

import java.util.List;

import com.netflix.netflixclone.dto.EpisodesRequest;
import com.netflix.netflixclone.entities.Episodes;
import com.netflix.netflixclone.exception.EpisodesException;
import com.netflix.netflixclone.exception.SeasonsException;
import com.netflix.netflixclone.exception.TVShowsException;

public interface EpisodesService {
   
	public Episodes addEpisode(EpisodesRequest request) throws EpisodesException, SeasonsException, TVShowsException;
	
	public Episodes findEpisodeById(Long episodeId) throws EpisodesException;
	
	public Episodes updateEpisode(Long episodeId, Episodes episodes) throws EpisodesException, SeasonsException, TVShowsException;
	
	public Episodes deleteEpisode(Long episodeId) throws EpisodesException;	
	
	public List<Episodes> getEpisodesBySeasonId(Long seasonId) throws EpisodesException;

	List<Episodes> getEpisodesByTvShowId(Long tvShowId) throws EpisodesException;
	
}
