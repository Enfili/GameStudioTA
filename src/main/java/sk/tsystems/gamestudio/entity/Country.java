package sk.tsystems.gamestudio.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue
    private long ident;

    @Column(length = 128, nullable = false, unique = true)
    private String country;

    @OneToMany(mappedBy = "ident")
    private List<Player> players;

    public Country() {
    }

    public Country(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Country: " + this.country;
    }

    public String getCountry() {
        return country;
    }

    public long getIdent() {
        return ident;
    }

    public void setIdent(long ident) {
        this.ident = ident;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
