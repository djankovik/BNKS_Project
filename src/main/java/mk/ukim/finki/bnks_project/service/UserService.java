package mk.ukim.finki.bnks_project.service;


import mk.ukim.finki.bnks_project.model.User;

public interface UserService {
    User register(String username, String password);
    User login(String username, String password);
}
