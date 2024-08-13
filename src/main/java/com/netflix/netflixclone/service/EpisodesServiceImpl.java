package com.netflix.netflixclone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.netflixclone.dto.EpisodesRequest;
import com.netflix.netflixclone.entities.Episodes;
import com.netflix.netflixclone.entities.Seasons;
import com.netflix.netflixclone.entities.TVShows;
import com.netflix.netflixclone.exception.EpisodesException;
import com.netflix.netflixclone.repository.EpisodesRepository;
import com.netflix.netflixclone.repository.SeasonsRepository;
import com.netflix.netflixclone.repository.TVShowsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EpisodesServiceImpl implements EpisodesService {

    @Autowired
    private EpisodesRepository episodesRepository;

    @Autowired
    private SeasonsRepository seasonsRepository;

    @Autowired
    private TVShowsRepository tvShowsRepository;

    @Override
    public Episodes createEpisode(EpisodesRequest request) throws EpisodesException {
        

        return episodesRepository.save();
    }

    @Override
    public Episodes findEpisodeById(Long episodeId) throws EpisodesException {
        return episodesRepository.findById(episodeId)
                .orElseThrow(() -> new EpisodesException("Episode not found with id " + episodeId));
    }

    @Override
    public Episodes updateEpisode(Long episodeId, EpisodesRequest request) throws EpisodesException {
        if (request == null || episodeId == null) {
            throw new EpisodesException("Invalid episode request");
        }

        Episodes existingEpisode = findEpisodeById(episodeId);
        existingEpisode.setEpisodeName(request.getEpisodeName());
        existingEpisode.setDescription(request.getDescription());
        existingEpisode.setReleaseDate(request.getReleaseDate());
        existingEpisode.setDurationMinutes(request.getDurationMinutes());

        // Optionally, you could update the season if it changes
        if (request.getSeasonId() != null) {
            Optional<Seasons> seasonOptional = seasonsRepository.findById(request.getSeasonId());
            if (seasonOptional.isPresent()) {
                Seasons season = seasonOptional.get();
                existingEpisode.setSeason(season);

                // Fetch the TV Show by ID and associate it with the season
                Optional<TVShows> tvShowOptional = tvShowsRepository.findById(season.getTvShowId());
                if (tvShowOptional.isPresent()) {
                    TVShows tvShow = tvShowOptional.get();
                    existingEpisode.setTvShow(tvShow);
                }
            }
        }

        return episodesRepository.save(existingEpisode);
    }

    @Override
    public Episodes deleteEpisode(Long episodeId) throws EpisodesException {
        Episodes episode = findEpisodeById(episodeId);
        episodesRepository.deleteById(episodeId);
        return episode;
    }

    @Override
    public List<Episodes> getEpisodesBySeasonId(Long seasonId) throws EpisodesException {
        List<Episodes> episodes = episodesRepository.findBySeasonId(seasonId);
        if (episodes.isEmpty()) {
            throw new EpisodesException("No episodes found for season id " + seasonId);
        }
        return episodes;
    }

    @Override
    public List<Episodes> getEpisodesByTvShowId(Long tvShowId) throws EpisodesException {
        List<Episodes> episodes = episodesRepository.findByTvShowId(tvShowId);
        if (episodes.isEmpty()) {
            throw new EpisodesException("No episodes found for TV Show id " + tvShowId);
        }
        return episodes;
    }
}