package com.netflix.netflixclone.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.netflixclone.dto.SeasonsRequest;
import com.netflix.netflixclone.entities.Seasons;
import com.netflix.netflixclone.entities.TVShows;
import com.netflix.netflixclone.exception.SeasonsException;
import com.netflix.netflixclone.exception.TVShowsException;
import com.netflix.netflixclone.repository.SeasonsRepository;

@Service
public class SeasonsServiceImpl implements SeasonsService {
	
	@Autowired
	private SeasonsRepository seasonsRepository;
	
	@Autowired
	private TVShowsService tvShowsService;

	@Override
	public Seasons registerSeason(SeasonsRequest request) throws SeasonsException, TVShowsException {
		TVShows findTVShows = tvShowsService.findById(request.getTvShowId());
        if (findTVShows == null) {
            throw new TVShowsException("TV show not found with Id" + request.getTvShowId());
        }
        
        Seasons newSeason = new Seasons();
        newSeason.setSeasonName(request.getSeasonName());
        newSeason.setEpisodes(request.getEpisodes());
        newSeason.setTvShow(findTVShows); 
        
        return seasonsRepository.save(newSeason);
	}

	@Override
	public Seasons updateSeason(Seasons seasons, Long seasonsId) throws SeasonsException, TVShowsException {
		TVShows findTVShows = tvShowsService.findById(seasons.getTvShow().getTvshowId());
        if (findTVShows == null) {
            throw new TVShowsException("TV show not found with Id" + seasons.getTvShow().getTvshowId());
        }
		
		Optional<Seasons> seasonOptional = seasonsRepository.findById(seasonsId);
        if (!seasonOptional.isPresent()) {
            throw new SeasonsException("Season not found with" +seasonsId);
        }
        
        Seasons season = seasonOptional.get();
        season.setSeasonName(seasons.getSeasonName());
        season.setEpisodes(seasons.getEpisodes());
        season.setTvShow(findTVShows);
        
        return seasonsRepository.save(season);
	}

	@Override
	public Seasons findSeasonById(Long seasonId) throws SeasonsException {
		Optional<Seasons> seasonsOptional = seasonsRepository.findById(seasonId);
        if (!seasonsOptional.isPresent()) {
            throw new SeasonsException("Season not found");
        }
        return seasonsOptional.get();
	}

	@Override
	public Seasons deleteSeasonById(Long seasonId) throws SeasonsException {
		Optional<Seasons> seasonsOptional = seasonsRepository.findById(seasonId);
        if (!seasonsOptional.isPresent()) {
            throw new SeasonsException("Season not found");
        }
        
        Seasons season = seasonsOptional.get();
        seasonsRepository.delete(season);
        return season;
	}

	@Override
	public Iterable<Seasons> findAllSeasons() {
		return seasonsRepository.findAll();
	}
}
