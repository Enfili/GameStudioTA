package test;

import sk.tsystems.gamestudio.entity.Score;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sk.tsystems.gamestudio.service.ScoreService;
import sk.tsystems.gamestudio.service.ScoreServiceFile;

import java.util.Date;

public class ScoreServiceTest {
    private ScoreService scoreService = new ScoreServiceFile();

    @Test
    public void testReset() {
        scoreService.addScore(new Score("sk/tsystems/gamestudio", "janko_hrasko", 100, new Date()));
        scoreService.reset();
        assertEquals(0, scoreService.getBestScores("sk/tsystems/gamestudio").size());
    }

    @Test
    public void testAddScore() {
        scoreService.reset();
        Date date = new Date();
        scoreService.addScore(new Score("sk/tsystems/gamestudio", "janko_hrasko", 100, date));

        var scores = scoreService.getBestScores("sk/tsystems/gamestudio");
        assertEquals(1, scores.size());
        assertEquals("sk/tsystems/gamestudio", scores.get(0).getGame());
        assertEquals("janko_hrasko", scores.get(0).getUsername());
        assertEquals(100, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());
    }

    @Test
    public void testGetBestScores() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("sk/tsystems/gamestudio", "Peto", 140, date));
        scoreService.addScore(new Score("sk/tsystems/gamestudio", "Katka", 150, date));
        scoreService.addScore(new Score("tiles", "Zuzka", 290, date));
        scoreService.addScore(new Score("sk/tsystems/gamestudio", "Jergus", 100, date));

        var scores = scoreService.getBestScores("sk/tsystems/gamestudio");

        assertEquals(3, scores.size());

        assertEquals("sk/tsystems/gamestudio", scores.get(0).getGame());
        assertEquals("Katka", scores.get(0).getUsername());
        assertEquals(150, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("sk/tsystems/gamestudio", scores.get(1).getGame());
        assertEquals("Peto", scores.get(1).getUsername());
        assertEquals(140, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("sk/tsystems/gamestudio", scores.get(2).getGame());
        assertEquals("Jergus", scores.get(2).getUsername());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());
    }
}
