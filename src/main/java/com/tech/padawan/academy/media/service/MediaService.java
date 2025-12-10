package com.tech.padawan.academy.media.service;

import com.tech.padawan.academy.global.AcademyConfiguration;
import com.tech.padawan.academy.global.exceptions.StorageException;
import com.tech.padawan.academy.media.dto.CreateMediaDTO;
import com.tech.padawan.academy.media.model.Media;
import com.tech.padawan.academy.media.model.MediaType;
import com.tech.padawan.academy.media.repository.MediaRepository;
import com.tech.padawan.academy.media.service.exception.MediaAlreadyExistsException;
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

            // Cria o diretório se não existir
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Define o caminho final (Ex: /uploads/VIDEO/aula01.mp4)
            // Normaliza o nome para evitar nomes como "../arquivo.txt"
            Path destinationFile = directoryPath.resolve(dto.name()).normalize();

            // Segurança: Garante que o arquivo está sendo salvo DENTRO da pasta permitida
            if (!destinationFile.getParent().equals(directoryPath)) {
                throw new StorageException("Cannot store file outside current directory.");
            }

            if (Files.exists(destinationFile)) {
                throw new MediaAlreadyExistsException("This media already exists in storage");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            Media newMedia = Media.builder()
                    .name(dto.name())
                    .type(dto.type())
                    .url(destinationFile.toString())
                    .build();

            return repository.save(newMedia);

        } catch (MediaAlreadyExistsException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll(MediaType type) {
        try {
            Path filesDir = this.rootLocation.resolve(type.toString());

            // Se a pasta não existe (nenhum arquivo desse tipo foi salvo), retorna stream vazio
            if (!Files.exists(filesDir)) {
                return Stream.empty();
            }

            return Files.walk(filesDir, 1)
                    .filter(path -> !path.equals(filesDir))
                    // Retorna o caminho relativo (Ex: VIDEO/meuvideo.mp4) em vez do absoluto
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
}