package com.hellFire.QuizGame.exceptions;

public class BusinessException extends RuntimeException{

    public BusinessException(String message, ErrorCode errorCode){
        super(message);
    }
}
