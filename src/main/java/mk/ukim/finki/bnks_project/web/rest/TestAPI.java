package mk.ukim.finki.bnks_project.web.rest;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path = "/api/signedendpoint", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class TestAPI {

//    private final VerificationService verificationService;

//    public SignedEndpointAPI(VerificationService verificationService) {
//        this.verificationService = verificationService;
//    }

    @GetMapping("/gettest")
    public @ResponseBody void getTest(HttpServletRequest request,
                                             HttpServletResponse response) {
        System.out.println(request.getMethod());
        System.out.println(request.getRemoteHost());
        System.out.println(request.getServerName());
        System.out.println(request.getPathInfo());
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURI().substring(request.getContextPath().length()));
        System.out.println(request.getDateHeader("If-Modified-Since"));
        System.out.println(request.getDateHeader("If-Unmodified-Since"));
        System.out.println("HEADERS:");
        for(String header:Collections.list(request.getHeaderNames())){
            System.out.println("\t"+header+" : "+request.getHeader(header));
        }
    }
    @PostMapping("/posttest")
    public @ResponseBody void postTest(HttpServletRequest request,
                                             HttpServletResponse response) {
        System.out.println(request.getMethod());
        System.out.println(request.getRemoteHost());
        System.out.println(request.getServerName());
        System.out.println("HEADERS:");
        for(String header:Collections.list(request.getHeaderNames())){
            System.out.println("\t"+header+" : "+request.getHeader(header));
        }
        System.out.println("BODY:");
        try {
            String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
