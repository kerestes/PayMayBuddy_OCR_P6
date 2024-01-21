package fr.paymybuddy.spring.api.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException (String message){
        super(message);
    }
}
