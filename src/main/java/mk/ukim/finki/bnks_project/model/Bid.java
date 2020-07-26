package mk.ukim.finki.bnks_project.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="BIDS")
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long bid_id;
    Double value;

    @ManyToOne
    @JoinColumn(name = "auctionitem_id", referencedColumnName = "auction_id")
    AuctionItem auctionItem;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "username")
    User user;

    public Bid(long id,Double val){
        this.bid_id = id;
        this.value = val;
    }

    public Bid(Double value, AuctionItem au, User u) {
        this.value = value;
        this.auctionItem = au;
        this.user = u;
    }

    @Override
    public String toString(){
        return "[bid_id: "+bid_id+", value: "+value+", auction_id: "+auctionItem.getAuction_id()+", user_id: "+user.getUsername()+"]";
    }
    public BidDto getBidDto(){
        return new BidDto(this.bid_id,this.auctionItem.getAuction_id(),this.user.getUsername(),this.value);
    }
}
