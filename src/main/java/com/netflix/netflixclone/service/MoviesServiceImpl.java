package com.netflix.netflixclone.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.netflixclone.dto.MoviesRequest;
import com.netflix.netflixclone.entities.Movies;
import com.netflix.netflixclone.exception.MoviesException;
import com.netflix.netflixclone.repository.MoviesRepository;

@Service
public class MoviesServiceImpl implements MoviesService {
    
    @Autowired
    private MoviesRepository moviesRepository;

    @Override
    public Movies registerRequest(MoviesRequest request) throws MoviesException {
        if (request == null || request.getMovieName() == null || request.getMovieName().isEmpty()) {
            throw new MoviesException("Invalid movie request");
        }
        
        Optional<Movies> existingMovie = moviesRepository.findByMovieName(request.getMovieName());
        if (existingMovie.isPresent()) {
            throw new MoviesException("Movie with title '" + request.getMovieName() + "' already exists");
        }
        
        Movies newMovie = new Movies();
        newMovie.setMovieName(request.getMovieName());
        newMovie.setImage(request.getImage());
        newMovie.setDescription(request.getDescription());
        newMovie.setReleaseDate(request.getReleaseDate());
        newMovie.setGenre(request.getGenre());
        
        return moviesRepository.save(newMovie);
    }

    @Override
    public Movies findById(Long movieId) throws MoviesException {
        return moviesRepository.findById(movieId)
                .orElseThrow(() -> new MoviesException("Movie not found with id " + movieId));
    }

    @Override
    public Movies updateRequest(MoviesRequest request, Long movieId) throws MoviesException {
        if (request == null || movieId == null) {
            throw new MoviesException("Invalid movie request");
        }

        Movies existingMovie = findById(movieId);
        existingMovie.setMovieName(request.getMovieName());
        existingMovie.setGenre(request.getGenre());
        existingMovie.setDescription(request.getDescription());
        existingMovie.setReleaseDate(request.getReleaseDate());
        existingMovie.setImage(request.getImage());
        
        return moviesRepository.save(existingMovie);
    }

    @Override
    public void deleteById(Long movieId) throws MoviesException {
        if (movieId == null) {
            throw new MoviesException("Invalid movie id");
        }
        
        Movies existingMovie = findById(movieId);
        System.out.println(existingMovie);
        moviesRepository.deleteById(movieId);
    }

    @Override
    public Iterable<Movies> findAll() {
        return moviesRepository.findAll();
    }

    @Override
    public Movies searchByTitle(String movieName) throws MoviesException {
        if (movieName == null || movieName.isEmpty()) {
            throw new MoviesException("Invalid title");
        }
        
        return moviesRepository.findByMovieName(movieName)
                .orElseThrow(() -> new MoviesException("Movie not found with title: " + movieName));
    }

    @Override
    public List<Movies> searchByGenre(String genre) throws MoviesException {
        if (genre == null || genre.isEmpty()) {
            throw new MoviesException("Invalid genre");
        }

        List<Movies> moviesByGenre = moviesRepository.findAll().stream()
                .filter(movie -> genre.equals(movie.getGenre()))
                .collect(Collectors.toList());

        if (moviesByGenre.isEmpty()) {
            throw new MoviesException("No movies found for genre '" + genre + "'");
        }

        return moviesByGenre;
    }
}
