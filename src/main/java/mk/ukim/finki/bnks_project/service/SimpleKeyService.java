package mk.ukim.finki.bnks_project.service;

import mk.ukim.finki.bnks_project.model.SimpleKey;

public interface SimpleKeyService {
    SimpleKey saveKey(String keyId, String algorithm, String key);
    void deleteKey(String keyId);
}
