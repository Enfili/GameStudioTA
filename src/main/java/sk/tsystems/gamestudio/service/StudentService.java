package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Student;
import sk.tsystems.gamestudio.entity.StudyGroup;

import java.util.List;

public interface StudentService {

    void addStudent(Student student);

    List<Student> getStudents(StudyGroup studyGroup);

    void reset();
}
