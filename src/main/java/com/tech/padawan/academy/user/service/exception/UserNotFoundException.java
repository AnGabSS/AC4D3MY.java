package com.tech.padawan.academy.user.service.exception;

import com.tech.padawan.academy.shared.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
