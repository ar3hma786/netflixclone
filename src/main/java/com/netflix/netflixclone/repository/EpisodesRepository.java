package com.netflix.netflixclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.netflix.netflixclone.entities.Episodes;

@Repository
public interface EpisodesRepository extends JpaRepository<Episodes, Long> {

    List<Episodes> findBySeasonId(Long seasonId);

    @Query("SELECT e FROM Episodes e WHERE e.episodeName = :episodeName")
    Optional<Episodes> findByEpisodeName(@Param("episodeName") String episodeName);

	List<Episodes> findByTvShowId(Long tvShowId);

}
