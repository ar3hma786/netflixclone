package com.netflix.netflixclone.service;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.netflix.netflixclone.dto.EpisodesRequest;
import com.netflix.netflixclone.entities.Episodes;
import com.netflix.netflixclone.entities.Seasons;
import com.netflix.netflixclone.entities.TVShows;
import com.netflix.netflixclone.exception.EpisodesException;
import com.netflix.netflixclone.exception.SeasonsException;
import com.netflix.netflixclone.exception.TVShowsException;
import com.netflix.netflixclone.repository.EpisodesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodesServiceImpl implements EpisodesService {

    @Autowired
    private EpisodesRepository episodesRepository;

    @Autowired
    private SeasonsService seasonsService;

    @Autowired
    private TVShowsService tvShowsService;

    @Override
    public Episodes addEpisode(EpisodesRequest request) throws EpisodesException, SeasonsException, TVShowsException  {
    	TVShows findTVShows = tvShowsService.findById(request.getTvShowId());
    	
    	if (findTVShows == null) {
            throw new TVShowsException("TV show not found with Id" +request.getTvShowId());
        }
    	
    	Seasons findSeason = seasonsService.findSeasonById(request.getSeasonId());
    	
    	if (findSeason == null) {
            throw new SeasonsException("Season not found with Id" + request.getSeasonId());
        }
    	
    	Episodes episodes = new Episodes();
    	episodes.setEpisodeName(request.getEpisodeName());
    	episodes.setDescription(request.getDescription());
    	episodes.setReleaseDate(request.getReleaseDate());
    	episodes.setDurationMinutes(request.getDurationMinutes());
        episodes.setSeason(findSeason);

        return episodesRepository.save(episodes);
    }

    @Override
    public Episodes findEpisodeById(Long episodeId) throws EpisodesException {
        return episodesRepository.findById(episodeId)
                .orElseThrow(() -> new EpisodesException("Episode not found with id " + episodeId));
    }

    @Override
    public Episodes updateEpisode(Long episodeId, Episodes episodes) throws EpisodesException, SeasonsException, TVShowsException {
    	Seasons findSeason = seasonsService.findSeasonById(episodes.getSeason().getSeasonId());
    	
    	if (findSeason == null) {
            throw new SeasonsException("Season not found with Id" + episodes.getSeason().getSeasonId());
        }
    	
    	Optional<Episodes> existingEpisode = episodesRepository.findById(episodeId);
    	
    	if (!existingEpisode.isPresent()) {
            throw new EpisodesException("Eppisode not found with id" +episodeId);
        }
    	
    	Episodes existEpisodes = existingEpisode.get();
    	
    	existEpisodes.setEpisodeName(episodes.getEpisodeName());
    	existEpisodes.setDescription(episodes.getDescription());
    	existEpisodes.setReleaseDate(episodes.getReleaseDate());
    	existEpisodes.setDurationMinutes(episodes.getDurationMinutes());											
    	existEpisodes.setSeason(findSeason);
    	return episodesRepository.save(episodes);
        
    }

    @Override
    public Episodes deleteEpisode(Long episodeId) throws EpisodesException {
        Episodes episode = findEpisodeById(episodeId);
        episodesRepository.deleteById(episodeId);
        return episode;
    }

    @Override
    public List<Episodes> getEpisodesBySeasonId(Long seasonId) throws EpisodesException {
        List<Episodes> episodes = episodesRepository.findBySeasonId(seasonId);
        if (episodes.isEmpty()) {
            throw new EpisodesException("No episodes found for season id " + seasonId);
        }
        return episodes;
    }

    @Override
    public List<Episodes> getEpisodesByTvShowId(Long tvShowId) throws EpisodesException {
        List<Episodes> episodes = episodesRepository.findByTvShowId(tvShowId);
        if (episodes.isEmpty()) {
            throw new EpisodesException("No episodes found for TV Show id " + tvShowId);
        }
        return episodes;
    }
}