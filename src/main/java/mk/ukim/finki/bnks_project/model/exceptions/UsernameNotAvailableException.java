package mk.ukim.finki.bnks_project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UsernameNotAvailableException extends RuntimeException{
    public UsernameNotAvailableException(String username){
        super("Username ["+username+"] is not available!");
    }
}