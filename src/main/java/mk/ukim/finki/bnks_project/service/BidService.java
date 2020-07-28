package mk.ukim.finki.bnks_project.service;

import mk.ukim.finki.bnks_project.model.Bid;
import mk.ukim.finki.bnks_project.model.BidDto;

import java.util.List;

public interface BidService {
    List<BidDto> getBidsForUser(String userId);
    void saveBid(Bid b);
}
