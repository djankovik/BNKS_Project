package mk.ukim.finki.bnks_project.repository.jpa;

import mk.ukim.finki.bnks_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User,String> {
}
