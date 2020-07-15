package mk.ukim.finki.bnks_project.repository.jpa;

import mk.ukim.finki.bnks_project.model.SimpleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaSimpleKeyRepository  extends JpaRepository<SimpleKey,Long> {
    @Query("SELECT sk.keyId FROM SimpleKey sk " +
            "WHERE sk.keyForUser=:userId")
    List<Long> getAllKeyIdsForUser(String userId);

}
