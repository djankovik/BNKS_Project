package mk.ukim.finki.bnks_project.repository;

import mk.ukim.finki.bnks_project.model.SimpleKey;

public interface SimpleKeyRepository {
    SimpleKey save(SimpleKey sk);
    SimpleKey get(String keyId);
    void delete(String keyId);
}
