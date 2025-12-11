package com.tech.padawan.academy.playlist.service;

import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.playlist.dto.FormPlaylistDTO;
import com.tech.padawan.academy.playlist.dto.ListPlaylistDTO;
import com.tech.padawan.academy.playlist.model.Playlist;

public interface IPlaylistService {
    Playlist create(FormPlaylistDTO dto);
    Playlist addVideo(Long id, Media video);
    Playlist removeVideo(Long playlistId, Long videoId);
    ListPlaylistDTO list();
    Playlist getById(Long id);
    Playlist update(Long id, FormPlaylistDTO dto);
    void delete(Long id);

}
