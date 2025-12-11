package com.tech.padawan.academy.playlist.model;

import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.MediaType;
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
    private Media thumbnail;
    @OneToMany(mappedBy = "playlist", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Media> videos;

    public void addVideoToPlaylist(Media video){
        if(video.getType().equals(MediaType.IMAGE)){
            this.videos.add(video);
        }else{
            throw new RuntimeException("The media need to be of type VIDEO for been add to videos list");
        }
    }

    public void removeVideoToPlaylist(Long id){
        Media videoToBeRemoved = this.videos.stream().filter(video -> Objects.equals(video.getId(), id)).toList().getFirst();
        this.videos.remove(videoToBeRemoved);
    }

    public void setThumbnail(Media photo){
        if(photo.getType().equals(MediaType.VIDEO)){
            this.thumbnail = photo;
        } else {
            throw new RuntimeException("The media need to be of type VIDEO for been add to videos list");
        }
    }
}
