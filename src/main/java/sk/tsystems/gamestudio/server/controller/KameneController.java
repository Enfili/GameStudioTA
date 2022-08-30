package sk.tsystems.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.kamene.core.*;
import sk.tsystems.gamestudio.kamene.userInterface.MoveOutOfFieldException;
import sk.tsystems.gamestudio.minesweeper.core.TooManyMinesException;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/kamene")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class KameneController {

    private final String GAME = "kamene";
    private final int ROW_COUNT = 4;
    private final int COLUMN_COUNT = 4;
//    private Field field = new Field(ROW_COUNT, COLUMN_COUNT);
    private Field field;
    private final int NUMBER_OF_SHUFFLE_MOVES = 3;

    private boolean newGame = true;
    private int numberOfMoves = 0;

    private boolean isPlaying = true;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserController userController;

    @RequestMapping
    public String kamene(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column,
                         Model model) throws MoveOutOfFieldException {
        if (newGame) {
            this.field = new Field(ROW_COUNT, COLUMN_COUNT);
            field.shuffle(NUMBER_OF_SHUFFLE_MOVES);
            newGame = false;
        }

        if (row != null && column != null) {
            String shift = canShift(row, column);
            if (shift != null) {
                field.shiftStone(shift);
                numberOfMoves++;
            }
        }
        if (field.isSolved()) {
            isPlaying = false;
            int score = NUMBER_OF_SHUFFLE_MOVES * 10 - numberOfMoves;
            if (userController.isLogged())
                scoreService.addScore(new Score(GAME, userController.getLoggedUser(), score, new Date()));
        }

        prepareModel(model);
        return "kamene";
    }

    @RequestMapping("/new")
    public String newGame(Model model) {
        field = new Field(ROW_COUNT, COLUMN_COUNT);
        field.shuffle(NUMBER_OF_SHUFFLE_MOVES);
        numberOfMoves = 0;
        if (!field.isSolved())
            isPlaying = true;
        prepareModel(model);
        return "kamene";
    }

    @RequestMapping("/comment")
    public String addComment(String comment, Model model) {
        if (userController.isLogged())
            commentService.addComment(new Comment(GAME, userController.getLoggedUser(), comment, new Date()));
        prepareModel(model);
        return "kamene";
    }

    @RequestMapping("/rating")
    public String addRating(int rating, Model model) {
        if (userController.isLogged())
            ratingService.setRating(new Rating(GAME, userController.getLoggedUser(), rating, new Date()));
        prepareModel(model);
        return "kamene";
    }

    @RequestMapping("/asynch")
    public String loadInAsynchMode() {
        if (this.field == null) {
            generateNewField();
        }
        return "kameneAsynch";
    }

    @RequestMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field processUserInputJson(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column) throws MoveOutOfFieldException {
        if (row != null && column != null) {
            String shift = canShift(row, column);
            if (shift != null) {
                this.field.shiftStone(shift);
                this.numberOfMoves++;
            }
        }
        if (field.isSolved() && userController.isLogged()) {
            scoreService.addScore(new Score(GAME, userController.getLoggedUser(), NUMBER_OF_SHUFFLE_MOVES * 10 - numberOfMoves, new Date()));
        }
        return this.field;
    }

    @RequestMapping(value = "/jsonnew", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field newGameJson() {
        generateNewField();
        return this.field;
    }

    @RequestMapping(value = "/jsoncomment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addComment(@RequestBody String comment) {
        if (userController.isLogged())
            commentService.addComment(new Comment(GAME, userController.getLoggedUser(), comment, new Date()));
    }

    @RequestMapping(value = "/jsonrating", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void setRating(@RequestBody String rating) {
        if (userController.isLogged())
            ratingService.setRating(new Rating(GAME, userController.getLoggedUser(), Integer.parseInt(rating), new Date()));
    }

    private void generateNewField() {
        this.field = new Field(ROW_COUNT, COLUMN_COUNT);
        this.field.shuffle(NUMBER_OF_SHUFFLE_MOVES);
        this.numberOfMoves = 0;
    }

    private String canShift(int row, int column) {
        if (field.getStones()[row][column] == null)
            return null;
        if (column - 1 >= 0 && field.getStones()[row][column - 1] == null)
            return "a";
        if (column + 1 < field.getColumnCount() && field.getStones()[row][column + 1] == null)
            return "d";
        if (row - 1 >= 0 && field.getStones()[row - 1][column] == null)
            return "w";
        if (row + 1 < field.getRowCount() && field.getStones()[row + 1][column] == null)
            return "s";
        return null;
    }

    public String getStoneText(Stone stone) {
        if (stone != null)
            return String.valueOf(stone.getValue());
        else
            return " ";
    }

    private void prepareModel(Model model) {
        model.addAttribute("field", field.getStones());
        model.addAttribute("playing", isPlaying);
        model.addAttribute("bestScores", scoreService.getBestScores(GAME));
        model.addAttribute("comments", commentService.getComments(GAME));
        model.addAttribute("rating", ratingService.getAverageRating(GAME));
    }
}
