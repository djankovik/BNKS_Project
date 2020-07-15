package mk.ukim.finki.bnks_project.bootstrap;

import lombok.Getter;
import mk.ukim.finki.bnks_project.model.AuctionItem;
import mk.ukim.finki.bnks_project.model.Bid;
import mk.ukim.finki.bnks_project.model.User;
import mk.ukim.finki.bnks_project.repository.jpa.JpaBidRepository;
import mk.ukim.finki.bnks_project.repository.jpa.JpaItemRepository;
import mk.ukim.finki.bnks_project.repository.jpa.JpaUserRepository;
import mk.ukim.finki.bnks_project.service.kdc.KDCService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DataHolder {
    public static final List<AuctionItem> auctionitems = new ArrayList<>();
    public static final List<Bid> bids = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();

    public final JpaItemRepository itemRepository;
    public final JpaBidRepository bidRepository;
    public final JpaUserRepository userRepository;
    public final KDCService KDC;

    public DataHolder(JpaItemRepository itemRepository, JpaBidRepository bidRepository, JpaUserRepository userRepository, KDCService kdc) {
        this.itemRepository = itemRepository;
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
        KDC = kdc;
    }

    @PostConstruct
    public void init() throws Exception {

        if (this.userRepository.count()==0 && this.bidRepository.count() == 0 && this.itemRepository.count() == 0) {
            users.add(new User("test1","OM5LdoG3rgljBrJmy/PZuA==","zGs4hXKKv5AqMryDN/AzeE8YSQNdYo5qVPnGZa7tGpsSVlalmAde9Udi1ZP9kCHIsRA8GTtzoyS/khg3P4WeKQ=="));
            users.add(new User("test2","y0/uGiDUMyue7Wh2yASvqQ==","UEiq2dJTWa0/P2+EkbB8CkCxBjHWA9W4Yd6EAxPZrSuNJ6XDePbYEJp2u3r7S2/nm6UpJMlf7UqpGMzbKovFzw=="));
            users.add(new User("test3","uLaHBdZFvIOUI5/Zs51qIw==","TQaHQRmMyo8gV4bIfrY+DH57htWbWtvrw7OU7boPeqZ/Mbc7Yd9jIb+Jm0ZNk9/WF3RnyczcSQUUJPf9/RMZ7g=="));
            userRepository.saveAll(users);
            KDC.generateKey("test1");
            KDC.generateKey("test2");
            KDC.generateKey("test3");

            bids.add(new Bid(1, 700.0));
            bids.add(new Bid(2, 300.0));
            bids.add(new Bid(3, 350.0));
            bids.add(new Bid(4, 220.0));
            bids.add(new Bid(5, 700.0));
            bids.add(new Bid(6, 20.0));
            bids.add(new Bid(7, 300.0));
            bids.add(new Bid(8, 250.0));
            bids.add(new Bid(9, 50.0));
            bids.add(new Bid(10, 100.0));
            bidRepository.saveAll(bids);
            auctionitems.add(new AuctionItem(1, "ProductA", 650.0));
            auctionitems.add(new AuctionItem(2, "ProductB", 120.0));
            auctionitems.add(new AuctionItem(3, "ProductC", 210.0));
            auctionitems.add(new AuctionItem(4, "ProductD", 900.0));
            auctionitems.add(new AuctionItem(5, "ProductE", 13.0));
            auctionitems.add(new AuctionItem(6, "ProductF", 56.0));
            auctionitems.add(new AuctionItem(7, "ProductG", 100.0));
            auctionitems.add(new AuctionItem(8, "ProductH", 200.0));
            auctionitems.add(new AuctionItem(9, "ProductI", 45.0));
            auctionitems.add(new AuctionItem(10, "ProductJ", 5.0));

            bids.get(0).setAuctionItem(auctionitems.get(0));
            auctionitems.get(0).addBid(bids.get(0));
            bids.get(0).setUser(users.get(0));
            bids.get(1).setAuctionItem(auctionitems.get(1));
            auctionitems.get(1).addBid(bids.get(1));
            bids.get(1).setUser(users.get(2));
            bids.get(2).setAuctionItem(auctionitems.get(1));
            auctionitems.get(1).addBid(bids.get(2));
            bids.get(2).setUser(users.get(0));
            bids.get(3).setAuctionItem(auctionitems.get(2));
            auctionitems.get(2).addBid(bids.get(3));
            bids.get(3).setUser(users.get(2));
            bids.get(4).setAuctionItem(auctionitems.get(3));
            auctionitems.get(3).addBid(bids.get(4));
            bids.get(4).setUser(users.get(0));
            bids.get(5).setAuctionItem(auctionitems.get(4));
            auctionitems.get(4).addBid(bids.get(5));
            bids.get(5).setUser(users.get(0));
            bids.get(6).setAuctionItem(auctionitems.get(6));
            auctionitems.get(6).addBid(bids.get(6));
            bids.get(6).setUser(users.get(2));
            bids.get(7).setAuctionItem(auctionitems.get(7));
            auctionitems.get(7).addBid(bids.get(7));
            bids.get(7).setUser(users.get(1));
            bids.get(8).setAuctionItem(auctionitems.get(8));
            auctionitems.get(8).addBid(bids.get(8));
            bids.get(8).setUser(users.get(1));
            bids.get(9).setAuctionItem(auctionitems.get(8));
            auctionitems.get(8).addBid(bids.get(9));
            bids.get(9).setUser(users.get(1));

            System.out.println("USERS\n" + users.toString());
            System.out.println("AU\n" + auctionitems.toString());
            System.out.println("BIDs\n" + bids.toString());

            itemRepository.saveAll(auctionitems);
            bidRepository.saveAll(bids);
        }
    }
}