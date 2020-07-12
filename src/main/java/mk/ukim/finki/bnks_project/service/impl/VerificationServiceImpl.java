package mk.ukim.finki.bnks_project.service.impl;

import mk.ukim.finki.bnks_project.model.SimpleKey;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchSimpleKeyException;
import mk.ukim.finki.bnks_project.repository.jpa.JpaSimpleKeyRepository;
import mk.ukim.finki.bnks_project.service.VerificationService;
import mk.ukim.finki.bnks_project.utils.ReqUtils;
import mk.ukim.finki.bnks_project.utils.VerificationUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class VerificationServiceImpl implements VerificationService {
    private final JpaSimpleKeyRepository simpleKeyRepository;
    private List<String> mustIncludeHeaders;

    public VerificationServiceImpl(JpaSimpleKeyRepository simpleKeyRepository) {
        this.simpleKeyRepository = simpleKeyRepository;
        mustIncludeHeaders = Arrays.asList("(request-target)","date"); //TODO: maybe placement shouldn't be here
    }

    @Override
    public boolean verifyRequestSignature(HttpServletRequest request){
        Map<String,String> requestHeaders = ReqUtils.getRequestHeaders(request);
        Map<String,String> signatureHeader = ReqUtils.processSignatureHeader(requestHeaders.get("signature"));
        String signingString = ReqUtils.getSigningString(requestHeaders,signatureHeader); //this is what is being signed
        SimpleKey sk = simpleKeyRepository.findById(signatureHeader.get("keyId")).orElseThrow(()-> new NoSuchSimpleKeyException(signatureHeader.get("keyId")));
        String signatureOnRequest = signatureHeader.get("signature");
        return VerificationUtils.verify(signingString,signatureOnRequest,sk.getKey(),sk.getAlgorithm());
    }
}
