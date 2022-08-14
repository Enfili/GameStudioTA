package sk.tsystems.gamestudio.minesweeper;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.entity.Test;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.ScoreServiceJDBC;
import sk.tsystems.gamestudio.service.TestService;
import sk.tsystems.gamestudio.service.TestServiceJDBC;

import java.util.Date;
import java.util.List;

public class PlaygroundJDBC {

    public static void main(String[] args) throws Exception {
//        ScoreService scoreService = new ScoreServiceJDBC();
//        scoreService.addScore(new Score("sk/tsystems/gamestudio", "David", 456, new Date()));
//        var scores = scoreService.getBestScores("sk/tsystems/gamestudio");
//        System.out.println(scores);

        TestService testService = new TestServiceJDBC();
//        testService.addTest(new Test(2, "minesweeper", "test2"));
//        testService.addTest(new Test(1, "minesweeper", "test2"));
//        testService.addTest(new Test(3, "minesweeper", "test3"));
//        testService.addTest(new Test(1, "kamene", "test1"));
        testService.reset();
//        List<Test> tests = testService.getTests("minesweeper");
//        System.out.println(tests);
    }
}
