package sk.tsystems.gamestudio.entity;

import javax.persistence.*;

@Entity
//@Table(uniqueConstraints={@UniqueConstraint(name = "UniqueUserNameAndFullName", columnNames = {"userName", "fullName"})})
public class Player {

    @Id
    @GeneratedValue
    private long ident;

    @Column(length = 32, nullable = false, unique = true)
    private String userName;

    @Column(length = 128, nullable = false)
    private String fullName;

    @Column(columnDefinition = "INT CHECK(self_evaluation BETWEEN 1 AND 10) NOT NULL")
    private int selfEvaluation;

    @ManyToOne
    @JoinColumn(name = "Country.ident", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "Occupation.ident", nullable = false)
    private Occupation occupation;

    public Player() {
    }

    public Player(String userName, String fullName, int selfEvaluation, Country country, Occupation occupation) {
        this.userName = userName;
        this.fullName = fullName;
        this.selfEvaluation = selfEvaluation;
        this.country = country;
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "Username: " + userName + '\'' +
                ", fullName " + fullName + '\'' +
                ", selfEvaluation " + selfEvaluation +
                ", " + country +
                ", " + occupation;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public int getSelfEvaluation() {
        return selfEvaluation;
    }

    public Country getCountry() {
        return country;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public long getIdent() {
        return ident;
    }

    public void setIdent(long ident) {
        this.ident = ident;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSelfEvaluation(int selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
