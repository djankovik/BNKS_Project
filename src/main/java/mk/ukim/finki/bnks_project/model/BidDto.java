package mk.ukim.finki.bnks_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BidDto {
    Long bid_id;
    Long auction_id;
    String user_id;
    Double value;
}
