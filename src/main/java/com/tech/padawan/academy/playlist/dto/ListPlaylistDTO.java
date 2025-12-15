package com.tech.padawan.academy.playlist.dto;

import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.playlist.model.Playlist;

public record ListPlaylistDTO(
        String name,
        String department,
        String thumbnailPath
) {

    public static ListPlaylistDTO fromEntity(Playlist entity){
        return new ListPlaylistDTO(
                entity.getName(),
                entity.getDepartment(),
                entity.getThumbnailPath()
        );
    }
}
