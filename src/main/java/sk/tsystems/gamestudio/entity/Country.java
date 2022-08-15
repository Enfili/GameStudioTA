package sk.tsystems.gamestudio.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Country {

    @Id
    @GeneratedValue
    private int ident;

    @Column(length = 128, nullable = false, unique = true)
    private String Country;

    @OneToMany(mappedBy = "ident")
    private List<Player> players;

    public Country() {
    }

    public Country(String country) {
        Country = country;
    }

    @Override
    public String toString() {
        return "Country{" +
                "ident=" + ident +
                ", Country='" + Country + '\'' +
                '}';
    }

    public String getCountry() {
        return Country;
    }
}
