package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Occupation;

import java.util.List;

public interface OccupationService {

    public List<Occupation> getOccupations();

    public void addOccupation(Occupation  occupation);
}
