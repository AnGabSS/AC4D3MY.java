package com.tech.padawan.academy.playlist.dto;

import com.tech.padawan.academy.global.utils.UrlGeneratorService;
import com.tech.padawan.academy.playlist.model.Playlist;

import java.util.List;
import java.util.stream.Collectors;

public record ListPlaylistByDepartmentDTO(
        String department,
        List<ListPlaylistDTO> playlists
) {

    public static List<ListPlaylistByDepartmentDTO> fromEntity(List<Playlist> playlists, UrlGeneratorService urlGeneratorService){
        return playlists.stream()
                .collect(Collectors.groupingBy(Playlist::getDepartment))
                .entrySet()
                .stream()
                .map(
                        (entry) -> new ListPlaylistByDepartmentDTO(entry.getKey(), entry.getValue().stream().map(e -> ListPlaylistDTO.fromEntity(e, urlGeneratorService)).toList())
                ).toList();
    };
}
