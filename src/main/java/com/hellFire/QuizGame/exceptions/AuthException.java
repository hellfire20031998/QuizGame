package com.hellFire.QuizGame.exceptions;

public class AuthException extends BusinessException {
    public AuthException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}