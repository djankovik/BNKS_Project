package mk.ukim.finki.bnks_project.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class VerificationUtils {

    private static boolean verifyRSA(String signature,String plainText, String publicKeyEncoded) throws Exception {
        byte[] decodedPublic = Base64.getDecoder().decode(publicKeyEncoded);
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublic));
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = Base64.getDecoder().decode(signature); //because signature is 64 encoded when sent

        return publicSignature.verify(signatureBytes);
    }

    public static boolean verifyHMAC(String signature, String plainText, String encodedSecretKey) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        byte [] decodedKey = Base64.getDecoder().decode(encodedSecretKey);
        SecretKeySpec secret_key = new SecretKeySpec(decodedKey, "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte [] bytehash = sha256_HMAC.doFinal(plainText.getBytes());
        String hash = Base64.getEncoder().encodeToString(bytehash);
        System.out.println(signature);
        System.out.println(hash);
        return hash.compareTo(signature) == 0;
    }
    public static boolean verify(String plainText, String signature, String key, String algorithm) {
        System.out.println("plaintext: "+plainText);
        System.out.println("signature: "+signature);
        System.out.println("key: "+key);
        System.out.println("algorithm: "+algorithm);
        if(algorithm.toLowerCase().contains("rsa")) {
            try {
                boolean result = verifyRSA(signature,plainText,key);
                System.out.println(result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        else if(algorithm.toLowerCase().contains("hmac")) {
            try {
                return verifyHMAC(signature,plainText,key);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        else return false;
            //throw new NoSuchAlgorithmException();
    }
}

