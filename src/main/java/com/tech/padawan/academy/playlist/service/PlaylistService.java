package com.tech.padawan.academy.playlist.service;

import com.tech.padawan.academy.media.dto.CreateMediaDTO;
import com.tech.padawan.academy.media.model.Image;
import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.MediaType;
import com.tech.padawan.academy.media.model.Video;
import com.tech.padawan.academy.media.service.IMediaService;
import com.tech.padawan.academy.media.service.MediaService;
import com.tech.padawan.academy.playlist.dto.FormPlaylistDTO;
import com.tech.padawan.academy.playlist.dto.ListPlaylistDTO;
import com.tech.padawan.academy.playlist.model.Playlist;
import com.tech.padawan.academy.playlist.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService implements IPlaylistService {

    private final PlaylistRepository repository;
    private final IMediaService mediaService;

    public PlaylistService(PlaylistRepository repository, IMediaService mediaService){
        this.repository = repository;
        this.mediaService = mediaService;
    }

    @Override
    public Playlist create(FormPlaylistDTO dto) {
        CreateMediaDTO mediaDTO = new CreateMediaDTO(
                "Thumbnail for playlist - " + dto.name(),
                MediaType.IMAGE,
                dto.thumbnail()
        );
        Media image = mediaService.create(mediaDTO);
        Playlist playlistToBeCreated = Playlist.builder()
                .name(dto.name())
                .department(dto.department())
                .thumbnailPath(image.getUrl())
                .build();
        return repository.save(playlistToBeCreated);
    }

    @Override
    public Playlist addVideo(Long id, Video video) {
        Playlist playlist = repository.findById(id).orElseThrow(() -> new RuntimeException("Playlist not exists"));
        playlist.addVideoToPlaylist(video);
        return repository.save(playlist);
    }

    @Override
    public Playlist removeVideo(Long playlistId, Long videoId) {
        Playlist playlist = repository.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist not exists"));
        playlist.removeVideoToPlaylist(videoId);
        return repository.save(playlist);
    }

    @Override
    public List<ListPlaylistDTO> list() {
        List<Playlist> videos = repository.findAll();
        return videos.stream().map(ListPlaylistDTO::fromEntity).toList();
    }

    @Override
    public Playlist getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Playlist not exists"));
    }

    @Override
    public Playlist update(Long id, FormPlaylistDTO dto) {
        Playlist playlist = repository.findById(id).orElseThrow(() -> new RuntimeException("Playlist not exists"));
        playlist.setName(dto.name());
        playlist.setDepartment(dto.department());
        if(!dto.thumbnail().isEmpty()){
            mediaService.deleteByName("Thumbnail for playlist - " + dto.name());
            CreateMediaDTO mediaDTO = new CreateMediaDTO(
            "Thumbnail for playlist - " + playlist.getName(),
                 MediaType.IMAGE,
                 dto.thumbnail()
            );
            Media image = mediaService.create(mediaDTO);
            playlist.setThumbnailPath(image.getUrl());
        }
        return repository.save(playlist);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);

    }
}
