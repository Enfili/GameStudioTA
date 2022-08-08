package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {

    void addScore(Score score);

    List<Score> getBestScores(String game);

    void reset();
}
