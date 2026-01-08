package com.tech.padawan.academy.media.service;

import com.tech.padawan.academy.global.AcademyConfiguration;
import com.tech.padawan.academy.global.exceptions.StorageException;
import com.tech.padawan.academy.media.dto.CreateMediaDTO;
import com.tech.padawan.academy.media.model.Image;
import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.MediaType;
import com.tech.padawan.academy.media.model.Video;
import com.tech.padawan.academy.media.repository.MediaRepository;
import com.tech.padawan.academy.media.service.exception.MediaAlreadyExistsException;
import com.tech.padawan.academy.playlist.model.Playlist;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class MediaService implements IMediaService {

    private final MediaRepository repository;
    private final Path rootLocation; // Alterei de String para Path para facilitar o uso

    public MediaService(MediaRepository repository, AcademyConfiguration configuration) {
        this.repository = repository;
        // Já converte para Path no construtor para evitar chamadas repetitivas de Path.of
        this.rootLocation = Path.of(configuration.media_path());
    }

    @Override
    public Media create(CreateMediaDTO dto) {
        try {
            MultipartFile file = dto.file();

            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            // Define o diretório baseado no Tipo (Ex: /uploads/VIDEO)
            Path directoryPath = this.rootLocation.resolve(dto.type().toString());

            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            Path destinationFile = directoryPath.resolve(dto.name()).normalize();

            if (!destinationFile.getParent().equals(directoryPath)) {
                throw new StorageException("Cannot store file outside current directory.");
            }

            if (Files.exists(destinationFile)) {
                throw new MediaAlreadyExistsException("This media already exists in storage");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            Media newMedia = buildMediaEntity(dto, destinationFile.toString());

            return repository.save(newMedia);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    // Adicione o parâmetro Long playlistId
    private Media buildMediaEntity(CreateMediaDTO dto, String url) {
        switch (dto.type()) {
            case VIDEO -> {
                Playlist playlistRef = null;
                if (dto.playlistId() != null) {
                    playlistRef = Playlist.builder().id(dto.playlistId()).build();
                }

                return Video.builder()
                        .name(dto.name())
                        .url(url)
                        .playlist(playlistRef)
                        .build();
            }
            case IMAGE -> {
                return Image.builder()
                        .name(dto.name())
                        .url(url)
                        .build();
            }
            case null, default -> {
                throw new IllegalArgumentException("Unsupported media type: " + dto.type());
            }
        }
    }

    @Override
    public Stream<Path> loadAll(MediaType type) {
        try {
            Path filesDir = this.rootLocation.resolve(type.toString());

            if (!Files.exists(filesDir)) {
                return Stream.empty();
            }

            return Files.walk(filesDir, 1)
                    .filter(path -> !path.equals(filesDir))
                    .map(this.rootLocation::relativize);

        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Media findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }
}