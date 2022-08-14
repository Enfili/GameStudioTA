package sk.tsystems.gamestudio.entity;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private int ident;
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "StudyGroup.ident", nullable = false)
    private StudyGroup studyGroup;

    public Student() {
    }

    public Student(String firstName, String lastName, StudyGroup studyGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studyGroup = studyGroup;
    }

    @Override
    public String toString() {
        return "Student{" +
                "ident=" + ident +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studyGroup=" + studyGroup +
                '}';
    }
}
