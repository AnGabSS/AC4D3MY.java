package com.tech.padawan.academy.global;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("academy")
public record AcademyConfiguration(String secret_key, String jwt_issuer, String front_url, String media_path) {
}
