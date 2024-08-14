package com.netflix.netflixclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.netflix.netflixclone.dto.TVShowsRequest;
import com.netflix.netflixclone.entities.TVShows;
import com.netflix.netflixclone.entities.User;
import com.netflix.netflixclone.exception.TVShowsException;
import com.netflix.netflixclone.exception.UserException;
import com.netflix.netflixclone.service.TVShowsService;
import com.netflix.netflixclone.service.UserService;

@RestController
@RequestMapping("/api/tvshows")
public class TVShowsController {
	
	@Autowired
	private TVShowsService tvShowsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
    public ResponseEntity<?> registerRequest(@RequestBody TVShowsRequest request, @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            TVShows tvShow = tvShowsService.registerRequest(request);
            return new ResponseEntity<>(tvShow, HttpStatus.CREATED);
        } catch (TVShowsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering TV show", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PutMapping("/{tvshowsId}")
    public ResponseEntity<?> updateTVShows(@RequestBody TVShows tvShows, @PathVariable Long tvshowsId, @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            TVShows updatedTVShow = tvShowsService.updateTVShows(tvShows, tvshowsId);
            return new ResponseEntity<>(updatedTVShow, HttpStatus.OK);
        } catch (TVShowsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating TV show", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/{tvshowId}")
    public ResponseEntity<?> findById(@PathVariable Long tvshowId, @RequestHeader("Authorization") String jwt) throws UserException {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            TVShows tvShow = tvShowsService.findById(tvshowId);
            return new ResponseEntity<>(tvShow, HttpStatus.OK);
        } catch (TVShowsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
	
	@DeleteMapping("/{tvshowId}")
    public ResponseEntity<?> deleteById(@PathVariable Long tvshowId, @RequestHeader("Authorization") String jwt) {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            if (user == null) {
                return new ResponseEntity<>("User not authorized", HttpStatus.UNAUTHORIZED);
            }
            TVShows deletedTVShow = tvShowsService.deleteById(tvshowId);
            return new ResponseEntity<>(deletedTVShow, HttpStatus.OK);
        } catch (TVShowsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting TV show", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/title")
    public ResponseEntity<?> searchByTitle(@RequestParam String tvShowName) {
        try {
            TVShows tvShow = tvShowsService.searchByTitle(tvShowName);
            return new ResponseEntity<>(tvShow, HttpStatus.OK);
        } catch (TVShowsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/")
    public ResponseEntity<Iterable<TVShows>> findAll() {
        Iterable<TVShows> tvShows = tvShowsService.findAll();
        return new ResponseEntity<>(tvShows, HttpStatus.OK);
    }
	
	@GetMapping("/genre")
    public ResponseEntity<?> searchByGenre(@RequestParam String genre) {
        try {
            List<TVShows> tvShows = tvShowsService.searchByGenre(genre);
            return new ResponseEntity<>(tvShows, HttpStatus.OK);
        } catch (TVShowsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
