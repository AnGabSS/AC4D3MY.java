package com.tech.padawan.academy.media.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("IMAGE")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Image extends Media{
}
