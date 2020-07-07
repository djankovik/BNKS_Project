package mk.ukim.finki.bnks_project.repository.jpa;

import mk.ukim.finki.bnks_project.model.SimpleKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSimpleKeyRepository  extends JpaRepository<SimpleKey,String> {
}
