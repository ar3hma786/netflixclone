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
@Table(name = "EPISODES")
public class Episodes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeId;
    
    @Column(name = "EPISODE_NAME", nullable = false)
    private String episodeName;
    
    @ManyToOne
    private Seasons season;
    
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
    
    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;
    
    @Column(name = "DURATION_MINUTES")
    private int durationMinutes;
}
