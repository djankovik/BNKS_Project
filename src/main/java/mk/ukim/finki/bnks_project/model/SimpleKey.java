package mk.ukim.finki.bnks_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SIMPLEKEYENTITIES")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleKey {
    @Id
    private String keyId;
    private String algorithm;
    @Column(name = "shared_key",length=600)
    private String key;
}
