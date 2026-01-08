package com.tech.padawan.academy.media.controller;

import com.tech.padawan.academy.media.dto.CreateMediaDTO;
import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.MediaType;
import com.tech.padawan.academy.media.service.IMediaService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    private final IMediaService service;

    public MediaController(IMediaService service){
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<Stream<Path>> getAllFiles(@RequestParam(value = "type", defaultValue = "IMAGE")MediaType type){
        return ResponseEntity.ok(service.loadAll(type));
    }


    @GetMapping("/{type}/{filename:.+}")
    public ResponseEntity<Resource> getMedia(@PathVariable String type, @PathVariable String filename) {

        Resource file = service.load(type + "/" + filename);

        String contentType = null;
        try {
            contentType = Files.probeContentType(file.getFile().toPath());
        } catch (IOException _) {
        }

        if (contentType == null) {
            String lowerName = filename.toLowerCase();
            if (lowerName.endsWith(".mp4")) {
                contentType = "video/mp4";
            } else if (lowerName.endsWith(".webm")) {
                contentType = "video/webm";
            } else if (lowerName.endsWith(".png")) {
                contentType = "image/png";
            } else if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else {
                // Só cai aqui se realmente não soubermos o que é
                contentType = "application/octet-stream";
            }
        }

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
