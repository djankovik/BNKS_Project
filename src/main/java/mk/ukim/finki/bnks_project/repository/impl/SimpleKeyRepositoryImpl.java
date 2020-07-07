package mk.ukim.finki.bnks_project.repository.impl;

import mk.ukim.finki.bnks_project.model.SimpleKey;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchSimpleKeyException;
import mk.ukim.finki.bnks_project.repository.SimpleKeyRepository;
import mk.ukim.finki.bnks_project.repository.jpa.JpaSimpleKeyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleKeyRepositoryImpl implements SimpleKeyRepository {
    private final JpaSimpleKeyRepository jpaSimpleKeyRepository;

    public SimpleKeyRepositoryImpl(JpaSimpleKeyRepository jpaSimpleKeyRepository) {
        this.jpaSimpleKeyRepository = jpaSimpleKeyRepository;
    }

    @Override
    public SimpleKey save(SimpleKey sk) {
        return jpaSimpleKeyRepository.save(sk);
    }

    @Override
    public SimpleKey get(String keyId) {
        return jpaSimpleKeyRepository.findById(keyId).orElseThrow(() -> new NoSuchSimpleKeyException(keyId));
    }

    @Override
    public void delete(String keyId) {
        jpaSimpleKeyRepository.deleteById(keyId);
    }

}
