package com.netflix.netflixclone.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TVSHOWS")
public class TVShows {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tvshowId;
    
    @Column(name = "TVSHOW_NAME", nullable = false)
    private String tvshowName;
    
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
    
    @OneToMany(mappedBy = "tvShow")
    private List<Seasons> seasons = new ArrayList<>();
}
