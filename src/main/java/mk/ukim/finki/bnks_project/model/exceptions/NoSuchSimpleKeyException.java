package mk.ukim.finki.bnks_project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchSimpleKeyException extends RuntimeException{
    public NoSuchSimpleKeyException(String keyId){
        super("The key with id ["+keyId+"] couldn't be found in the database");
    }
}
