package mk.ukim.finki.bnks_project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchUserException extends Exception{
    public NoSuchUserException(String id){
        super("The user with id ["+id+"] couldn't be found in the database");
    }
}
