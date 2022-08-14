package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Student;
import sk.tsystems.gamestudio.entity.StudyGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class StudentServiceJPA implements StudentService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    @Override
    public List<Student> getStudents(StudyGroup studyGroup) {
        return entityManager
                .createQuery("select s from Student s where s.studyGroup = :studyGroup")
                .setParameter("studyGroup", studyGroup)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from student").executeUpdate();
    }
}
