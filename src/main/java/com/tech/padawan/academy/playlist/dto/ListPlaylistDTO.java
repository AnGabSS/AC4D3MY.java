package com.tech.padawan.academy.playlist.dto;

import com.tech.padawan.academy.playlist.model.Playlist;

import java.util.List;

public record ListPlaylistDTO(
        Long id,
        String name,
        String thumbnailPath
) {

    public static ListPlaylistDTO fromEntity(Playlist entity){
        return new ListPlaylistDTO(
                entity.getId(),
                entity.getName(),
                entity.getThumbnailPath()
        );
    }
}
