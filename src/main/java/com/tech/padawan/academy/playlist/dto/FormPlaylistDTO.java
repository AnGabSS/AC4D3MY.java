package com.tech.padawan.academy.playlist.dto;

import com.tech.padawan.academy.media.model.Image;
import org.springframework.web.multipart.MultipartFile;

public record FormPlaylistDTO(
        String name,
        String department,
        MultipartFile thumbnail
){
}
