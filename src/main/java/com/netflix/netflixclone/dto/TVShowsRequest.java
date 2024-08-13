package com.netflix.netflixclone.dto;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import com.netflix.netflixclone.entities.Seasons;
import com.netflix.netflixclone.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TVShowsRequest {
	
    private String tvshowName;
    
    private User user;
    
    private String image;
    
    private String description;
    
    private LocalDate releaseDate;
    
    private String genre;
    
    private List<Seasons> seasons = new ArrayList<>();

}
