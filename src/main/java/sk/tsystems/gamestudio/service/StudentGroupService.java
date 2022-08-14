package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.StudyGroup;

import java.util.List;

public interface StudentGroupService {

    void addStudyGroup(StudyGroup studyGroup);

    List<StudyGroup> getStudyGroups();

    void reset();
}
