package br.com.solutis.squad1.cartservice.exception;

import lombok.Getter;

@Getter
public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String message) {
        super(message);
    }
}
