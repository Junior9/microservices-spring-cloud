package com.cloud.exceptions;

public class SendEmailException extends RuntimeException{

    public SendEmailException (String message){
        super(message);
    }
}
