package com.tech.padawan.academy.global.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UrlGeneratorService {

    public String generatePublicURL(String absolutePath){
        if(absolutePath == null) return null;

        Path path = Paths.get(absolutePath);
        String fileName = path.getFileName().toString();

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/arquivos/")
                .path(fileName)
                .toUriString();    }
}
