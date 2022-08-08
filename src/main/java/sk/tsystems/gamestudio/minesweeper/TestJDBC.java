package sk.tsystems.gamestudio.minesweeper;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;

public class TestJDBC {

    public static void main(String[] args) throws Exception {
        ScoreService scoreService = new ScoreServiceJDBC();
        scoreService.addScore(new Score("sk/tsystems/gamestudio", "David", 456, new Date()));
        var scores = scoreService.getBestScores("sk/tsystems/gamestudio");
        System.out.println(scores);
    }
}
