package com.tech.padawan.academy.media.model;

import com.tech.padawan.academy.playlist.model.Playlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("VIDEO")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Video extends Media {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
}
