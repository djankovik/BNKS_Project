package mk.ukim.finki.bnks_project.service;

import mk.ukim.finki.bnks_project.model.AuctionItem;
import mk.ukim.finki.bnks_project.model.Bid;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchAuctionItemException;
import mk.ukim.finki.bnks_project.model.exceptions.NoSuchBidException;

import java.util.List;

public interface AuctionItemService {
    List<AuctionItem> getAllItems();
    AuctionItem findById(Long id) throws NoSuchAuctionItemException;
    AuctionItem addBidToAuctionItem(AuctionItem au, Bid bid) throws NoSuchBidException, NoSuchAuctionItemException;
}
