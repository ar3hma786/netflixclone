package com.netflix.netflixclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.netflix.netflixclone.entities.Movies;

public interface MoviesRepository extends JpaRepository<Movies, Long> {

    @Query("SELECT m FROM Movies m WHERE m.movieName = :movieName")
    Optional<Movies> findByMovieName(@Param("movieName") String movieName);
}
