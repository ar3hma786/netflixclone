package com.netflix.netflixclone.dto;

import java.time.LocalDate;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoviesRequest {
	
    private String movieName;
    
    private String image;
    
    private String description; 
    
    private LocalDate releaseDate;
    
    private String genre;
    
    private int durationMinutes;
}
