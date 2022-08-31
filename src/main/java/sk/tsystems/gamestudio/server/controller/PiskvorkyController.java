package sk.tsystems.gamestudio.server.controller;

import io.swagger.models.auth.In;
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
import sk.tsystems.gamestudio.minesweeper.core.Field;
import sk.tsystems.gamestudio.minesweeper.core.TooManyMinesException;
import sk.tsystems.gamestudio.piskvorky.Board;
import sk.tsystems.gamestudio.piskvorky.IfElseAI;
import sk.tsystems.gamestudio.piskvorky.InRow;
import sk.tsystems.gamestudio.piskvorky.Symbol;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/piskvorky")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PiskvorkyController {

    @Autowired
    UserController userController;
    @Autowired
    ScoreService scoreService;
    @Autowired
    RatingService ratingService;
    @Autowired
    CommentService commentService;

    private IfElseAI board;
    private final int NUMBER_OF_ROWS = 12;
    private final int NUMBER_OF_COLUMNS = 12;
    private final String GAME = "piskvorky";

    @RequestMapping
    public String piskvorky() {
        if (board == null) {
            board = new IfElseAI(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        }
        return "piskvorky";
    }

    @RequestMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public IfElseAI processUserInput(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column) {
        if (row != null && column != null) {
            if (board.validMove(row, column)) {
                processMove(row, column);
                int[] computerMove = board.findMove();
                processMove(computerMove[0], computerMove[1]);
            }
        }

        return this.board;
    }

    private void processMove(int row, int column) {
        board.drawSymbol(row, column);
        board.updateGame();
        if (!board.isPlaying() && userController.isLogged()) {
            scoreService.addScore(new Score(GAME, userController.getLoggedUser(), board.getScore()[0] - board.getScore()[1], new Date()));
        }
    }

    @RequestMapping(value = "/jsonnew", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public IfElseAI newGame() {
        board = new IfElseAI(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        board.updateGame();
        return this.board;
    }

    @RequestMapping(value = "/jsoncomment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addComment(@RequestBody String comment) {
        if (userController.isLogged() && userController.isUserAlreadyExists())
            commentService.addComment(new Comment(GAME, userController.getLoggedUser(), comment, new Date()));
    }

    @RequestMapping(value = "/jsonrating", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void setRating(@RequestBody String rating) {
        if (userController.isLogged() && userController.isUserAlreadyExists())
            ratingService.setRating(new Rating(GAME, userController.getLoggedUser(), Integer.parseInt(rating), new Date()));
    }
}
