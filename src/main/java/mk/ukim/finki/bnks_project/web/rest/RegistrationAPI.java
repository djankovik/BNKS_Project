package mk.ukim.finki.bnks_project.web.rest;

import mk.ukim.finki.bnks_project.model.User;
import mk.ukim.finki.bnks_project.model.UserDto;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchSimpleKeyException;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchUserException;
import mk.ukim.finki.bnks_project.service.UserService;
import mk.ukim.finki.bnks_project.service.kdc.KDCService;
import mk.ukim.finki.bnks_project.utils.JwtTokenUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/users/", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RegistrationAPI {
    private final UserService userService;
    private final KDCService KDC;

    public RegistrationAPI(UserService userService, KDCService kdc) {
        this.userService = userService;
        KDC = kdc;
    }

    @PostMapping("register")
    public String registerNewUser(@RequestBody UserDto dto, HttpServletResponse response) {
        try {
            User u = userService.register(dto.getUsername(), dto.getPassword());
            String token = userService.getTokenForUser(u);
            Long keyId = KDC.generateKey(u.getUsername());
            return token;
        } catch(Exception e) {
            e.printStackTrace();
            try {
                response.sendError(401,"A problem occured while registering. Bad/unfit credentials.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @PostMapping("login")
    public String loginUser(@RequestBody UserDto dto, HttpServletResponse response) {
       try {
            User u = userService.login(dto.getUsername(), dto.getPassword());
            String token = userService.getTokenForUser(u);
            return token;
        } catch(Exception e) {
            e.printStackTrace();
            try {
                response.sendError(401,"A problem occured while logging in. Bad credentials.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    @GetMapping("fetchall")
    public List<Long> getKeyIdsAvailableForUser(HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        //Validate JWT token
        String token = request.getHeader(JwtTokenUtil.header_name);
        if(token == null) response.sendError(401,"No JWT token specified. User not authenticated.");
        try {
            boolean valid = userService.validateToken(token);
            if(!valid) {
                response.sendError(401,"Invalid JWT token.");
                return null;
            }
        } catch (NoSuchUserException e) {
            e.printStackTrace();
            response.sendError(401,"JWT token is for an invalid user.");
            return null;
        }
        String username = userService.getUsernameFromToken(token);
        return KDC.getKeyIdsForUser(username);
    }

    @GetMapping("fetch")
    public String getKeyForUser(@RequestParam("keyId") Long keyId,HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        //Validate JWT token
        String token = request.getHeader(JwtTokenUtil.header_name);
        if(token == null) response.sendError(401,"No JWT token specified. User not authenticated.");
        try {
            boolean valid = userService.validateToken(token);
            if(!valid) {
                response.sendError(401,"Invalid JWT token.");
                return null;
            }
        } catch (NoSuchUserException e) {
            e.printStackTrace();
            response.sendError(401,"JWT token is for an invalid user.");
            return null;
        }
        String username = userService.getUsernameFromToken(token);
        try {
            String key = KDC.getKey(keyId,username);
            return key;
        } catch (NoSuchSimpleKeyException e) {
            e.printStackTrace();
            response.sendError(401,"No simple key was found.");
        }
        return null;
    }

}
