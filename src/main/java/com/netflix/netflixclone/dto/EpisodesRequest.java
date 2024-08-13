package com.netflix.netflixclone.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EpisodesRequest {
    
    private String episodeName;
    
    private int durationMinutes;
    
    private String description;
    
    private LocalDate releaseDate;
    
    private Long seasonId;
    
    private Long tvShowId;

}
