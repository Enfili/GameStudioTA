package sk.tsystems.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class StudyGroup {

    @Id
    @GeneratedValue
    private int ident;

    private String name;

    @OneToMany(mappedBy = "ident")
    private List<Student> students;

    public StudyGroup() {
    }

    public StudyGroup(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
