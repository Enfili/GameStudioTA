package sk.tsystems.gamestudio.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Occupation {

    @Id
    @GeneratedValue
    private int ident;

    @Column(length = 32, nullable = false, unique = true)
    private String Occupation;

    @OneToMany(mappedBy = "ident")
    private List<Player> players;

    public Occupation() {
    }

    public Occupation(String occupation) {
        Occupation = occupation;
    }

    @Override
    public String toString() {
        return "Occupation: " + Occupation;
    }

    public String getOccupation() {
        return Occupation;
    }
}
