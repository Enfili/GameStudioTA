package sk.tsystems.gamestudio.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Occupation {

    @Id
    @GeneratedValue
    private long ident;

    @Column(length = 32, nullable = false, unique = true)
    private String occupation;

    @OneToMany(mappedBy = "ident")
    private List<Player> players;

    public Occupation() {
    }

    public Occupation(String occupation) {
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Occupation: " + occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public long getIdent() {
        return ident;
    }

    public void setIdent(long ident) {
        this.ident = ident;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
