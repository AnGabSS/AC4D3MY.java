package com.tech.padawan.academy.playlist.dto;

import org.springframework.web.multipart.MultipartFile;

public record AddVideoToPlaylistDTO(
        String videoName,
        MultipartFile file) {
}
