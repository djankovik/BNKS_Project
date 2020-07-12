package mk.ukim.finki.bnks_project.service.impl;

import mk.ukim.finki.bnks_project.model.SimpleKey;
import mk.ukim.finki.bnks_project.repository.jpa.JpaSimpleKeyRepository;
import mk.ukim.finki.bnks_project.service.SimpleKeyService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SimpleKeyServiceImpl implements SimpleKeyService {

    private final JpaSimpleKeyRepository simpleKeyRepository;
    private List<String> signatureHeaders;
    public SimpleKeyServiceImpl(JpaSimpleKeyRepository simpleKeyRepository) {
        this.simpleKeyRepository = simpleKeyRepository;
        signatureHeaders = Arrays.asList("(request-target)","host","date","digest","content-length");
    }

    @Override
    public SimpleKey saveKey(String keyId, String algorithm, String key) {
        SimpleKey sk = new SimpleKey(keyId,algorithm,key);
        simpleKeyRepository.save(sk);
        return sk;
    }

    @Override
    public void deleteKey(String keyId) {
        simpleKeyRepository.deleteById(keyId);
    }
}
