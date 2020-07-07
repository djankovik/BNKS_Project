package mk.ukim.finki.bnks_project.web.rest;

import mk.ukim.finki.bnks_project.service.SimpleKeyService;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/simplekey/", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class SimpleKeyRegisterAPI {
    private final SimpleKeyService simpleKeyService;

    public SimpleKeyRegisterAPI(SimpleKeyService simpleKeyService) {
        this.simpleKeyService = simpleKeyService;
    }

    @GetMapping("dummy")
    public void addDummyData(){
        String keyId1 = "IugSHmgP3yQqbhPdycCzR9mvP8PuQi";
        String keyId2 = "FIEvId7VLjBalD0OBzw09IpBlfXHHq";
        String keyRSA = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvObSnJHLkuWWzv4opkAL87H7Z2Z70PFCaQHPy3pJHP56xwrh1vm0sQwKzVrfbRjAnp8zKEb7E5gRXbD0wEF/bjlRjK+grcc2gcDp/r/wl5QLd2+uDEul9CLw9BBsLd9agxl9CewrpYcOHZI40jDQTBPiU59bOAbBP4dEDO5C5MRt12mogEJRZ/1UPag7oB3eJ19oapcAiKY1xwtCr63+K++HpK1h9iiGzJ3bq9nJIoLNJt0l7YtTQMqpDSucGl4ispbWQ6JL8JVAtXmceXbQKgR0mCrgoyOxNjm4I95s1Pp0tqDkh1bQGZSZjs+BgOY+76X0AhZpMVMDInwG+OUS5QIDAQAB";
        String keyHMAC = "+0VvP9eYbSF1bXdkjbBrleBLojHB4xnTQQ5vet5NSNM=";
        simpleKeyService.saveKey(keyId1,"rsa-sha-256",keyRSA);
        simpleKeyService.saveKey(keyId2,"hmac-sha-256",keyHMAC);
    }
}
