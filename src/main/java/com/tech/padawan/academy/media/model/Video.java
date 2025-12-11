package com.tech.padawan.academy.media.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
}
