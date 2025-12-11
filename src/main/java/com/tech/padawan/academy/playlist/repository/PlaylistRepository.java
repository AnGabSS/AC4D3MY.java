package com.tech.padawan.academy.playlist.repository;

import com.tech.padawan.academy.playlist.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
