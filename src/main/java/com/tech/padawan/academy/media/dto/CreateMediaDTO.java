package com.tech.padawan.academy.media.dto;

import com.tech.padawan.academy.media.model.MediaType;
import org.springframework.web.multipart.MultipartFile;

public record CreateMediaDTO(
        String name,
        MediaType type,
        MultipartFile file
) {
}
