package com.netflix.netflixclone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.netflix.netflixclone.entities.Episodes;


public interface EpisodesRepository extends JpaRepository<Episodes, Long> {

	@Query("SELECT e FROM Episodes e WHERE e.season.id = :seasonId")
    List<Episodes> findBySeasonId(@Param("seasonId") Long seasonId);

	List<Episodes> findByTvShowId(Long tvShowId);

	

    

}
