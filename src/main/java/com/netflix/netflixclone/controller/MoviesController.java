package com.netflix.netflixclone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.netflix.netflixclone.dto.MoviesRequest;
import com.netflix.netflixclone.entities.Movies;
import com.netflix.netflixclone.entities.User;
import com.netflix.netflixclone.exception.MoviesException;
import com.netflix.netflixclone.exception.UserException;
import com.netflix.netflixclone.service.MoviesService;
import com.netflix.netflixclone.service.UserService;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    @Autowired
    private UserService userService;

    @Autowired
    private MoviesService movieService;

    @PostMapping("/")
    public ResponseEntity<?> registerRequest(@RequestBody MoviesRequest request,
                                             @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            Movies movie = movieService.registerRequest(request);
            return new ResponseEntity<>(movie, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering movie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> findById(@PathVariable Long movieId,
                                      @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            Movies movie = movieService.findById(movieId);
            if (movie == null) {
                return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (UserException | MoviesException e) {
            return new ResponseEntity<>("Error retrieving movie", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<?> updateRequest(@RequestBody Movies movies,
                                            @PathVariable Long movieId,
                                            @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            Movies movie = movieService.updateMovies(movies, movieId);
            if (movie == null) {
                return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (UserException | MoviesException e) {
            return new ResponseEntity<>("Error updating movie", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> deleteById(@PathVariable Long movieId,
                                         @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            movieService.deleteById(movieId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UserException | MoviesException e) {
            return new ResponseEntity<>("Error deleting movie", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Movies>> findAll() {
        try {
            Iterable<Movies> movies = movieService.findAll();
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByTitle(@RequestParam String title,
                                            @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            Movies movie = movieService.searchByTitle(title);
            if (movie == null) {
                return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (UserException | MoviesException e) {
            return new ResponseEntity<>("Error searching movie", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/genre")
    public ResponseEntity<?> searchByGenre(@RequestParam String genre) {
        try {
            Iterable<Movies> movies = movieService.searchByGenre(genre);
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (MoviesException e) {
            return new ResponseEntity<>("Error searching by genre", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
