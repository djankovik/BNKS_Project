package mk.ukim.finki.bnks_project.service.impl;

import mk.ukim.finki.bnks_project.model.User;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchUserException;
import mk.ukim.finki.bnks_project.model.exceptions.UsernameNotAvailableException;
import mk.ukim.finki.bnks_project.repository.jpa.JpaUserRepository;
import mk.ukim.finki.bnks_project.service.UserService;
import mk.ukim.finki.bnks_project.utils.JwtTokenUtil;
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
    private final JwtTokenUtil jwtTokenUtil;


    public UserServiceImpl(JpaUserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public User register(String username, String password) throws UsernameNotAvailableException{
        //check if such username exists
        if(userRepository.findById(username).isPresent()) throw new UsernameNotAvailableException(username);
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
    public User login(String username, String password) throws UsernameNotAvailableException {
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

    @Override
    public boolean validateToken(String token) throws NoSuchUserException {
        String id = jwtTokenUtil.getUsernameFromToken(token);
        User u = userRepository.findById(id).orElseThrow(() -> new NoSuchUserException(id));
        return jwtTokenUtil.validateToken(token,u);
    }

    @Override
    public String getTokenForUser(User u) {
        return jwtTokenUtil.generateToken(u);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return jwtTokenUtil.getUsernameFromToken(token);
    }

    @Override
    public User findUserById(String id) throws NoSuchUserException {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchUserException(id));
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
