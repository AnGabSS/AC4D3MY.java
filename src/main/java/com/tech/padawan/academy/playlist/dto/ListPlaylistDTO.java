package com.tech.padawan.academy.playlist.dto;

import com.tech.padawan.academy.media.dto.ListMediaDTO;
import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.Video;
import com.tech.padawan.academy.playlist.model.Playlist;

import java.util.List;

public record ListPlaylistDTO(
        Long id,
        String name,
        String department,
        String thumbnailPath,
        List<ListMediaDTO> videos
) {

    public static ListPlaylistDTO fromEntity(Playlist entity){
        return new ListPlaylistDTO(
                entity.getId(),
                entity.getName(),
                entity.getDepartment(),
                entity.getThumbnailPath(),
                entity.getVideos().stream().map(ListMediaDTO::fromEntity).toList()
        );
    }
}
