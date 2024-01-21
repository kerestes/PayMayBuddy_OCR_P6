package fr.paymybuddy.spring.api.exception;

public class UserDoesNotExistsException extends RuntimeException{
    public UserDoesNotExistsException (String message){
        super(message);
    }
}
