package com.netflix.netflixclone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.netflix.netflixclone.entities.Seasons;

public interface SeasonsRepository extends JpaRepository<Seasons, Long> {

}
