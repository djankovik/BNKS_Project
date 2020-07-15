package mk.ukim.finki.bnks_project.service.impl;

import mk.ukim.finki.bnks_project.model.Bid;
import mk.ukim.finki.bnks_project.model.BidDto;
import mk.ukim.finki.bnks_project.repository.jpa.JpaBidRepository;
import mk.ukim.finki.bnks_project.service.BidService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidServiceImpl implements BidService {
    private final JpaBidRepository bidRepository;

    public BidServiceImpl(JpaBidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public List<BidDto> getBidsForUser(String userId) {
        return bidRepository.getAllBidsForUser(userId).stream().map(b -> b.getBidDto()).collect(Collectors.toList());
    }

    @Override
    public Bid saveBid(Bid b) {
        return bidRepository.save(b);
    }
}
