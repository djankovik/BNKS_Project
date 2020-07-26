package mk.ukim.finki.bnks_project.service.kdc;

import mk.ukim.finki.bnks_project.model.SimpleKey;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchSimpleKeyException;
import mk.ukim.finki.bnks_project.repository.jpa.JpaSimpleKeyRepository;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class KDCMockServiceImpl implements KDCService {
    private final JpaSimpleKeyRepository jpaSimpleKeyRepository;

    public KDCMockServiceImpl(JpaSimpleKeyRepository jpaSimpleKeyRepository) {
        this.jpaSimpleKeyRepository = jpaSimpleKeyRepository;
    }

    @Override
    public Long generateKey(String forUser) {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("Hmacsha256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey key = keyGen.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        SimpleKey simpleKey = new SimpleKey(encodedKey,forUser);
        return jpaSimpleKeyRepository.save(simpleKey).getKeyId();
    }

    @Override
    public String getKey(Long keyId, String user) throws NoSuchSimpleKeyException {
        Optional<SimpleKey> opt = jpaSimpleKeyRepository.findById(keyId);
        if(opt.isEmpty()) throw new NoSuchSimpleKeyException(keyId.toString());
        if(opt.get().getKeyForUser().compareTo(user) != 0) throw new NoSuchSimpleKeyException(keyId.toString());
        return opt.get().getKey();
    }

    @Override
    public List<Long> getKeyIdsForUser(String userId) {
        List<Long> keyids = jpaSimpleKeyRepository.getAllKeyIdsForUser(userId);
        System.out.println(keyids);
        return keyids;
    }
}
