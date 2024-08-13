package com.netflix.netflixclone.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "SEASONS")
public class Seasons {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonId;
    
    @Column(name = "SEASON_NAME", nullable = false)
    private String seasonName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TV_SHOW_ID", nullable = false)
    private TVShows tvShow;
    
    @OneToMany(mappedBy = "season", fetch = FetchType.LAZY)
    private List<Episodes> episodes = new ArrayList<>();
}
