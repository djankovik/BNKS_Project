package mk.ukim.finki.bnks_project.model.exceptions;

public class NoSuchAuctionItemException extends Exception{
    public NoSuchAuctionItemException(Long id){
        super("The auction with id ["+id+"] couldn't be found in the database");
    }
}