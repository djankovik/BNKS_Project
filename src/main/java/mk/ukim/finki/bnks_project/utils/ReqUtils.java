package mk.ukim.finki.bnks_project.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class ReqUtils {

    public static String getSigningString(Map<String,String> requestHeaders,Map<String,String> signatureHeader){
        String[] headersUsedInSignature = signatureHeader.get("headers").split("\\s+");
        StringBuilder sb = new StringBuilder();
        for(String head:headersUsedInSignature){
            if(head.compareTo("(request-target)")==0){
                sb.append("(request-target): "
                        + requestHeaders.get("method")+" "+requestHeaders.get("routepath"));
                continue;
            }
            sb.append("\n");
            sb.append(head+": "+ requestHeaders.get(head));
        }
        String signingString = sb.toString();
        System.out.println("signing string: \n"+signingString);
        return signingString;
    }

    public static Map<String,String> getRequestHeaders(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        for(String header:Collections.list(request.getHeaderNames())){
            map.put(header,request.getHeader(header));
        }
        map.put("method",request.getMethod().toUpperCase());
        map.put("routepath",request.getRequestURI().substring(request.getContextPath().length()).toLowerCase());
        System.out.println(map.toString());
        return map;
    }

    public static Map<String,String> processSignatureHeader(String header){
        Map<String,String> map = new HashMap<>();

        String [] parts = header.replace("signature ","").replace("\"","").split(",");

        for(String signPart:parts){
            if(signPart.contains("keyId=")){
                map.put("keyId",signPart.replace("keyId=",""));
            } else if(signPart.contains("algorithm=")){
                map.put("algorithm",signPart.replace("algorithm=",""));
            } else if(signPart.contains("signature=")){
                map.put("signature",signPart.replace("signature=",""));
            } else if(signPart.contains("headers=")){
                map.put("headers",signPart.replace("headers=",""));
            }
        }
        System.out.println(map.toString());
        return map;
    }

    private static String getHashForText(String plainText){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);
        return encoded;
    }

    public static boolean validateBodyContentDigest(HttpServletRequest request){
        if(request.getHeader("digest") == null){
            return true;
        }

        String bodyPlainText = null;
        try {
            bodyPlainText = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String hashed = getHashForText(bodyPlainText);
        return request.getHeader("digest").compareTo(hashed.replace("SHA256=","")) == 0;
    }

    public static String recreateSignature(String algorithm,String method,String path,String timestamp, String body){
        Map<String,String> map = new HashMap<>();
        map.put("METHOD",method);map.put("TIMESTAMP",timestamp);map.put("PATH",path);map.put("BODY",body);
        String[] parts = algorithm.split("\\+");
        StringBuilder sb=new StringBuilder();
        for(String part:parts){
            sb.append(map.get(part));
        }
        return sb.toString();
    }
}
