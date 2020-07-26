package mk.ukim.finki.bnks_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USERS")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    private String username;
    private String salt;
    private String hashedSaltedPassword;
    @OneToMany(mappedBy="user")
    @JsonIgnore
    List<Bid> placedBids;
    
    public User(String username, String saltBase64, String hashedSaltedBase64) {
        this.username = username;
        this.salt = saltBase64;
        this.hashedSaltedPassword = hashedSaltedBase64;
        this.placedBids = new ArrayList<>();
    }
}