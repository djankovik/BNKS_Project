package mk.ukim.finki.bnks_project.repository.jpa;

import mk.ukim.finki.bnks_project.model.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaItemRepository extends JpaRepository<AuctionItem,Long> {
}
