package com.netflix.netflixclone.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.netflix.netflixclone.enums.ROLE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "USERS")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Enumerated(EnumType.STRING)
    private ROLE role;
    
    @OneToMany(mappedBy = "user")
    private List<Movies> movies = new ArrayList<>();
    
    @OneToMany(mappedBy = "user")
    private List<TVShows> tvshows = new ArrayList<>();
    
    private LocalDateTime datetime;
}
