package mk.ukim.finki.bnks_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="SIMPLEKEYS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(name="seq", initialValue=100, allocationSize=1000)
public class SimpleKey {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long keyId;
    @Column(name = "secret_key",length=600)
    private String key;
    private String keyForUser;
    public SimpleKey(String key,String user){
        this.key = key;
        this.keyForUser = user;
    }
}
