package com.tech.padawan.academy.playlist.service;

import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.playlist.dto.FormPlaylistDTO;
import com.tech.padawan.academy.playlist.dto.ListPlaylistDTO;
import com.tech.padawan.academy.playlist.model.Playlist;
import com.tech.padawan.academy.playlist.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService implements IPlaylistService {

    private final PlaylistRepository repository;


    public PlaylistService(PlaylistRepository repository){
        this.repository = repository;
    }

    @Override
    public Playlist create(FormPlaylistDTO dto) {
        Playlist playlistToBeCreated = Playlist.builder()
                .name(dto.name())
                .department(dto.department())
                .build();
        return repository.save(playlistToBeCreated);
    }

    @Override
    public Playlist addVideo(Long id, Media video) {
        return null;
    }

    @Override
    public Playlist removeVideo(Long playlistId, Long videoId) {
        return null;
    }

    @Override
    public ListPlaylistDTO list() {
        return null;
    }

    @Override
    public Playlist getById(Long id) {
        return null;
    }

    @Override
    public Playlist update(Long id, FormPlaylistDTO dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
