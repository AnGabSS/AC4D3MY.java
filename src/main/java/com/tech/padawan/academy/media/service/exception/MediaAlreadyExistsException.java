package com.tech.padawan.academy.media.service.exception;

import com.tech.padawan.academy.global.exceptions.AlreadyExistsException;

public class MediaAlreadyExistsException extends AlreadyExistsException {
    public MediaAlreadyExistsException(String message) {
        super(message);
    }
}
