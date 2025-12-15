package com.tech.padawan.academy.playlist.controller;

import com.tech.padawan.academy.playlist.dto.FormPlaylistDTO;
import com.tech.padawan.academy.playlist.model.Playlist;
import com.tech.padawan.academy.playlist.service.IPlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/playlist")
public class PlaylistController {

    private final IPlaylistService service;

    public PlaylistController(IPlaylistService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Playlist> create(@ModelAttribute FormPlaylistDTO dto){
        return ResponseEntity.status(201).body(service.create(dto));
    }

}
