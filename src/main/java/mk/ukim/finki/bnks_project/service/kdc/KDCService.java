package mk.ukim.finki.bnks_project.service.kdc;

import mk.ukim.finki.bnks_project.model.exceptions.NoSuchSimpleKeyException;

import java.util.List;

public interface KDCService {
    Long generateKey(String forUser);
    String getKey(Long keyId,String userId) throws NoSuchSimpleKeyException;
    List<Long> getKeyIdsForUser(String userId);
}
