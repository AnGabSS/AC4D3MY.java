package com.tech.padawan.academy.media.service;

import com.tech.padawan.academy.media.dto.CreateMediaDTO;
import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.MediaType;
import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IMediaService {
    Media create(CreateMediaDTO dto);
    Stream<Path> loadAll(MediaType type);
    Resource load(String filename);
}
