package com.netflix.netflixclone.service;

import java.util.List;

import com.netflix.netflixclone.dto.EpisodesRequest;
import com.netflix.netflixclone.entities.Episodes;
import com.netflix.netflixclone.exception.EpisodesException;

public interface EpisodesService {
   
	public Episodes registerEpisode(EpisodesRequest request) throws EpisodesException;
	
	public Episodes findEpisodeById(Long episodeId) throws EpisodesException;
	
	public Episodes updateEpisode(Long episodeId, EpisodesRequest request) throws EpisodesException;
	
	public Episodes deleteEpisode(Long episodeId) throws EpisodesException;	
	
	public List<Episodes> getEpisodesBySeasonId(Long seasonId) throws EpisodesException;

	List<Episodes> getEpisodesByTvShowId(Long tvShowId) throws EpisodesException;
	
}
