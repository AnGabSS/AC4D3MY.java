package com.tech.padawan.academy.media.dto;

import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.MediaType;
import org.springframework.web.multipart.MultipartFile;

public record ListMediaDTO(Long id,
                           String name,
                           String url) {

    public static ListMediaDTO fromEntity(Media media){
        return new ListMediaDTO(
                media.getId(),
                media.getName(),
                media.getUrl()
        );
    }

}
