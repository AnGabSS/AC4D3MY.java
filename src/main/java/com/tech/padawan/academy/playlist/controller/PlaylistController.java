package com.tech.padawan.academy.playlist.controller;

import com.tech.padawan.academy.media.dto.CreateMediaDTO;
import com.tech.padawan.academy.media.model.MediaType;
import com.tech.padawan.academy.media.model.Video;
import com.tech.padawan.academy.media.service.IMediaService;
import com.tech.padawan.academy.playlist.dto.AddVideoToPlaylistDTO;
import com.tech.padawan.academy.playlist.dto.FormPlaylistDTO;
import com.tech.padawan.academy.playlist.dto.ListPlaylistDTO;
import com.tech.padawan.academy.playlist.model.Playlist;
import com.tech.padawan.academy.playlist.service.IPlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist")
@Tag(name = "Playlist Controller", description = "Gerenciamento de Playlists e seus vídeos")
public class PlaylistController {

    private final IPlaylistService service;
    private final IMediaService mediaService;

    public PlaylistController(IPlaylistService service, IMediaService mediaService){
        this.service = service;
        this.mediaService = mediaService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    @Operation(summary = "Criar Playlist", description = "Cria uma nova playlist vazia ou com dados iniciais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Playlist criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Playlist> create(@ModelAttribute FormPlaylistDTO dto){
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar Playlists", description = "Retorna uma lista resumida de todas as playlists")
    public ResponseEntity<List<ListPlaylistDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes completos de uma playlist específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playlist encontrada"),
            @ApiResponse(responseCode = "404", description = "Playlist não encontrada")
    })
    public ResponseEntity<ListPlaylistDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "Atualizar Playlist", description = "Atualiza os dados cadastrais da playlist (nome, descrição, capa)")
    public ResponseEntity<Playlist> update(
            @Parameter(description = "ID da playlist") @PathVariable Long id,
            @ModelAttribute FormPlaylistDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Playlist", description = "Remove uma playlist do sistema")
    @ApiResponse(responseCode = "204", description = "Playlist removida com sucesso")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/{id}/video", consumes = {"multipart/form-data"})
    @Operation(summary = "Adicionar Vídeo", description = "Faz upload de um vídeo e o vincula a uma playlist existente")
    public ResponseEntity<Playlist> addVideoToPlaylist(
            @Parameter(description = "ID da playlist alvo") @PathVariable Long id,
            @ModelAttribute AddVideoToPlaylistDTO dto) {

        CreateMediaDTO videoDto = new CreateMediaDTO(dto.videoName(), MediaType.VIDEO, dto.file(), id);
        Video video = (Video) mediaService.create(videoDto);

        return ResponseEntity.ok(service.addVideo(id, video));
    }

    @DeleteMapping("/{id}/video/{videoId}")
    @Operation(summary = "Remover Vídeo", description = "Desvincula um vídeo da playlist (sem deletar a mídia original)")
    public ResponseEntity<Playlist> removeVideoFromPlaylist(
            @PathVariable Long id,
            @PathVariable Long videoId) {
        return ResponseEntity.ok(service.removeVideo(id, videoId));
    }
}