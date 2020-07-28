package mk.ukim.finki.bnks_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="AUCTIONITEMS")
@AllArgsConstructor
@NoArgsConstructor
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long auction_id;
    String sellerName;
    String itemName;
    String description;
    String shippingInfo;
    String paymentTypes;
    Date dateOpened;
    Date dateClosing;
    Double auctionStartedAtAmmount;
    Double currentHighestBidAmmount;
    @OneToMany(mappedBy="auctionItem")
    @JsonIgnore
    List<Bid> placedBids;

    public AuctionItem(long id,String itemName,Double start){
        this.auction_id = id;
        this.itemName = itemName;
        this.auctionStartedAtAmmount = start;
        this.currentHighestBidAmmount = 0.0;
        this.placedBids = new ArrayList<>();
    }
    public void addBid(Bid bid){
        placedBids.add(bid);
        Double highest = placedBids.stream().reduce((a,b)-> a.getValue().compareTo(b.getValue()) < 0?b:a).get().getValue();
        this.currentHighestBidAmmount = highest;
    }

    @Override
    public String toString(){
        return "[auction_id: "+auction_id+", item_name: "+itemName+", started_at: "+auctionStartedAtAmmount+", highest_bid: "+currentHighestBidAmmount+", BIDS: "+placedBids.toString()+"]";
    }

}
