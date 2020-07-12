package mk.ukim.finki.bnks_project.web.rest;

import mk.ukim.finki.bnks_project.model.User;
import mk.ukim.finki.bnks_project.model.UserDto;
import mk.ukim.finki.bnks_project.service.UserService;
import mk.ukim.finki.bnks_project.utils.JwtTokenUtil;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/api/users/", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RegistrationAPI {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public RegistrationAPI(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("register")
    public void registerNewUser(@RequestBody UserDto dto, HttpServletResponse response) {

        try {
            User u = userService.register(dto.getUsername(), dto.getPassword());
            String token = jwtTokenUtil.generateToken(u);
            response.addHeader("JWT",token);
        } catch(Exception e) {
            e.printStackTrace();
            try {
                response.sendError(401);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @PostMapping("login")
    public void loginUser(@RequestBody UserDto dto, HttpServletResponse response) {
        try {
            User u = userService.login(dto.getUsername(), dto.getPassword());
            String token = jwtTokenUtil.generateToken(u);
            response.addHeader("JWT",token);
        } catch(Exception e) {
            e.printStackTrace();
            try {
                response.sendError(401);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
