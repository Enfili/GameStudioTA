package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.StudyGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class StudentGroupServiceJPA implements StudentGroupService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addStudyGroup(StudyGroup studyGroup) {
        entityManager.persist(studyGroup);
    }

    @Override
    public List<StudyGroup> getStudyGroups() {
        return entityManager
                .createQuery("select sg from StudyGroup sg")
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from study_group").executeUpdate();
    }
}
