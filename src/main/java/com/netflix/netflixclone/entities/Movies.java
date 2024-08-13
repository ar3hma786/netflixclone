package com.netflix.netflixclone.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "MOVIES")
public class Movies {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    
    @Column(name = "MOVIE_NAME", nullable = false)
    private String movieName;
    
    @ManyToOne
    private User user;
    
    @Column(name = "IMAGE")
    private String image;
    
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    
    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;
    
    @Column(name = "GENRE")
    private String genre;
    
    @Column(name = "DURATION_MINUTES")
    private int durationMinutes;
}
