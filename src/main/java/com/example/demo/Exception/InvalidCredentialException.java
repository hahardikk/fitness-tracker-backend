package com.example.demo.Exception;

public class InvalidCredentialException extends RuntimeException{
   public InvalidCredentialException(String message){
        super(message);
    }
}
