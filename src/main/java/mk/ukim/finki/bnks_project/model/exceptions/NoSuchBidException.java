package mk.ukim.finki.bnks_project.model.exceptions;

public class NoSuchBidException extends Exception{
    public NoSuchBidException(Long id){
        super("The bid with id ["+id+"] couldn't be found in the database");
    }
}