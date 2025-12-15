package com.tech.padawan.academy.playlist.model;

import com.tech.padawan.academy.media.model.Image;
import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.MediaType;
import com.tech.padawan.academy.media.model.Video;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "playlists")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Department is required")
    private String department;
    @NotNull(message = "Thumbnail is required")
    private String thumbnailPath;
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Video> videos;

    public void addVideoToPlaylist(Video video){
        this.videos.add(video);
    }

    public void removeVideoToPlaylist(Long id){
        Video videoToBeRemoved = this.videos.stream().filter(video -> Objects.equals(video.getId(), id)).toList().getFirst();
        this.videos.remove(videoToBeRemoved);
    }

}
