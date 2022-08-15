package sk.tsystems.gamestudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Country {

    @Id
    @GeneratedValue
    private int ident;

    @Column(length = 128, nullable = false, unique = true)
    private String Country;

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
