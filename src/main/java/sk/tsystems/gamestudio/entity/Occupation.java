package sk.tsystems.gamestudio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Occupation {

    @Id
    @GeneratedValue
    private int ident;

    @Column(length = 32, nullable = false, unique = true)
    private String Occupation;

    public Occupation() {
    }

    public Occupation(String occupation) {
        Occupation = occupation;
    }

    @Override
    public String toString() {
        return "Occupation{" +
                "ident=" + ident +
                ", Occupation='" + Occupation + '\'' +
                '}';
    }

    public String getOccupation() {
        return Occupation;
    }
}
