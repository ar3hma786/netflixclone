package com.netflix.netflixclone.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.netflixclone.dto.TVShowsRequest;
import com.netflix.netflixclone.entities.TVShows;
import com.netflix.netflixclone.exception.TVShowsException;
import com.netflix.netflixclone.repository.TVShowsRepository;

@Service
public class TVShowsServiceImpl implements TVShowsService {
    
    @Autowired
    private TVShowsRepository tvShowsRepository;

    @Override
    public Iterable<TVShows> findAll() {
        return tvShowsRepository.findAll();
    }

    @Override
    public TVShows registerRequest(TVShowsRequest request) throws TVShowsException {
        if (request == null || request.getTvshowName() == null || request.getTvshowName().isEmpty()) {
            throw new TVShowsException("Invalid TV show request");
        }
        
        Optional<TVShows> existingTVShow = tvShowsRepository.findByTvshowName(request.getTvshowName());
        if (existingTVShow.isPresent()) {
            throw new TVShowsException("TV show with title '" + request.getTvshowName() + "' already exists");
        }
        
        TVShows newTVShows = new TVShows();
        newTVShows.setTvshowName(request.getTvshowName());
        newTVShows.setImage(request.getImage());
        newTVShows.setDescription(request.getDescription());
        newTVShows.setReleaseDate(request.getReleaseDate());
        newTVShows.setGenre(request.getGenre());
        newTVShows.setSeasons(request.getSeasons());
        
        return tvShowsRepository.save(newTVShows);
    }

    @Override
    public TVShows updateTVShows(TVShows tvShows, Long tvshowsId) throws TVShowsException {
        Optional<TVShows> existingTVShow = tvShowsRepository.findById(tvshowsId);
        if (!existingTVShow.isPresent()) {
            throw new TVShowsException("TV show with ID '" + tvshowsId + "' not found");
        }
        
        TVShows tvShowsToUpdate = existingTVShow.get();
        tvShowsToUpdate.setTvshowName(tvShows.getTvshowName());
        tvShowsToUpdate.setImage(tvShows.getImage());
        tvShowsToUpdate.setDescription(tvShows.getDescription());
        tvShowsToUpdate.setReleaseDate(tvShows.getReleaseDate());
        tvShowsToUpdate.setGenre(tvShows.getGenre());
        tvShowsToUpdate.setSeasons(tvShows.getSeasons());
        
        return tvShowsRepository.save(tvShowsToUpdate);
    }

    @Override
    public TVShows findById(Long tvshowId) throws TVShowsException {
        return tvShowsRepository.findById(tvshowId)
                .orElseThrow(() -> new TVShowsException("TV show with ID '" + tvshowId + "' not found"));
    }

    @Override
    public TVShows deleteById(Long tvshowId) throws TVShowsException {
        TVShows tvShow = findById(tvshowId);
        tvShowsRepository.deleteById(tvshowId);
        return tvShow;
    }

    @Override
    public TVShows searchByTitle(String tvShowName) throws TVShowsException {
        return tvShowsRepository.findByTvshowName(tvShowName)
                .orElseThrow(() -> new TVShowsException("TV show with title '" + tvShowName + "' not found"));
    }

    @Override
    public List<TVShows> searchByGenre(String genre) throws TVShowsException {
        if (genre == null || genre.isEmpty()) {
            throw new TVShowsException("Invalid genre");
        }
        List<TVShows> tvShowsByGenre = tvShowsRepository.findAll().stream()
            .filter(tvshow -> genre.equals(tvshow.getGenre()))
            .collect(Collectors.toList());

        if (tvShowsByGenre.isEmpty()) {
            throw new TVShowsException("No TV shows found for genre '" + genre + "'");
        }

        return tvShowsByGenre;
    }
}
