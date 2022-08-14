package sk.tsystems.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreWebServiceRest {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/{game}")
    private List<Score> getBestScores(@PathVariable String game) {
        return scoreService.getBestScores(game);
    }

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }
}
