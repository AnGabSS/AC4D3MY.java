package com.tech.padawan.academy.playlist.dto;

import com.tech.padawan.academy.media.model.Media;

public record ListPlaylistDTO(
        String name,
        String department,
        Media thumbnail
) {
}
