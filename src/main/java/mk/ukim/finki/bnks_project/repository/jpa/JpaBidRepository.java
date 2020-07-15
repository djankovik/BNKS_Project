package mk.ukim.finki.bnks_project.repository.jpa;

import mk.ukim.finki.bnks_project.model.Bid;
import mk.ukim.finki.bnks_project.model.BidDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaBidRepository extends JpaRepository<Bid,Long> {
    @Query("SELECT b FROM Bid b " +
            "WHERE b.user.username=:userId")
    List<Bid> getAllBidsForUser(String userId);
}
