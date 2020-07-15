package mk.ukim.finki.bnks_project.service.impl;

import mk.ukim.finki.bnks_project.model.AuctionItem;
import mk.ukim.finki.bnks_project.model.Bid;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchAuctionItemException;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchBidException;
import mk.ukim.finki.bnks_project.repository.jpa.JpaBidRepository;
import mk.ukim.finki.bnks_project.repository.jpa.JpaItemRepository;
import mk.ukim.finki.bnks_project.service.AuctionItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionItemServiceImpl implements AuctionItemService {
    private final JpaItemRepository itemRepository;

    public AuctionItemServiceImpl(JpaItemRepository repository) {
        this.itemRepository = repository;
    }

    @Override
    public List<AuctionItem> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public AuctionItem findById(Long id) throws NoSuchAuctionItemException {
        return itemRepository.findById(id).orElseThrow(() -> new NoSuchAuctionItemException(id));
    }

    @Override
    public AuctionItem addBidToAuctionItem(AuctionItem au, Bid bid) throws NoSuchBidException, NoSuchAuctionItemException {
        AuctionItem adb = itemRepository.findById(au.getAuction_id()).orElseThrow(() -> new NoSuchAuctionItemException(au.getAuction_id()));
        adb.addBid(bid);
        itemRepository.save(adb);
        return null;
    }
}
