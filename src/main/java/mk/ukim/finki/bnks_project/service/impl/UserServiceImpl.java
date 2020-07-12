package mk.ukim.finki.bnks_project.service.impl;

import mk.ukim.finki.bnks_project.model.User;
import mk.ukim.finki.bnks_project.model.exceptions.UsernameNotAvailableException;
import mk.ukim.finki.bnks_project.repository.jpa.JpaUserRepository;
import mk.ukim.finki.bnks_project.service.UserService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final JpaUserRepository userRepository;

    public UserServiceImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username, String password) {
        //check if such username exists
        userRepository.findById(username).ifPresent(x -> {throw new UsernameNotAvailableException(username);});
        byte [] pass = password.getBytes(StandardCharsets.UTF_8);
        byte [] salt = getRandomSalt(16);
        byte [] hashedSalted = calculateHashedSaltedPassword(pass,salt);
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashedSaltedBase64 = Base64.getEncoder().encodeToString(hashedSalted); //decode: Base64.getDecoder().decode(encodedString)
        User user = new User(username,saltBase64,hashedSaltedBase64);
        userRepository.save(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        //check if such username exists
        Optional<User> opt = userRepository.findById(username);
        if(opt.isEmpty()) throw new UsernameNotAvailableException(username);
        User user = opt.get();
        byte [] userSalt = Base64.getDecoder().decode(user.getSalt());
        byte [] currentPass = password.getBytes(StandardCharsets.UTF_8);
        byte [] hashedSaltedInput = calculateHashedSaltedPassword(currentPass,userSalt);
        String hashedSaltedInputBase64 = Base64.getEncoder().encodeToString(hashedSaltedInput);
        if(user.getHashedSaltedPassword().compareTo(hashedSaltedInputBase64)!=0)
            throw new IllegalArgumentException("The credentials led to an unsuccessful login");
        return user;
    }

    private byte[] getRandomSalt(int n){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[n];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] calculateHashedSaltedPassword(byte [] pass, byte [] salt){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md.update(pass);
        md.update(salt);
        return md.digest();
    }

}
