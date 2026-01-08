package com.tech.padawan.academy.playlist.service;

import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.Video;
import com.tech.padawan.academy.playlist.dto.FormPlaylistDTO;
import com.tech.padawan.academy.playlist.dto.ListPlaylistDTO;
import com.tech.padawan.academy.playlist.model.Playlist;

import java.util.List;

public interface IPlaylistService {
    Playlist create(FormPlaylistDTO dto);
    Playlist addVideo(Long id, Video video);
    Playlist removeVideo(Long playlistId, Long videoId);
    List<ListPlaylistDTO> list();
    ListPlaylistDTO getById(Long id);
    Playlist update(Long id, FormPlaylistDTO dto);
    void delete(Long id);

}
