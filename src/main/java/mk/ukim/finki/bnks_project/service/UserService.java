package mk.ukim.finki.bnks_project.service;


import mk.ukim.finki.bnks_project.model.User;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchSimpleKeyException;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchUserException;
import mk.ukim.finki.bnks_project.model.exceptions.UsernameNotAvailableException;

public interface UserService {
    User register(String username, String password) throws UsernameNotAvailableException;
    User login(String username, String password) throws UsernameNotAvailableException;
    boolean validateToken(String token) throws NoSuchUserException;
    String getTokenForUser(User u);
    String getUsernameFromToken(String token);
    User findUserById(String id) throws NoSuchUserException;
}
