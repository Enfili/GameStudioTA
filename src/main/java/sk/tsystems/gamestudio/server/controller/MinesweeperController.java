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
import sk.tsystems.gamestudio.minesweeper.core.*;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

import java.awt.*;
import java.util.Date;

@Controller
@RequestMapping("/minesweeper")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesweeperController {
    private final String GAME = "minesweeper";

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserController userController;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    /**
     * game field
     */
    private Field field;

    /**
     * false if opening tiles, true if marking tiles
     */
    private boolean marking = false;

    /**
     * false if finished (won or lost), true if playing the game
     */
    private boolean isPlaying = true;


    @RequestMapping
    public String processUserInput(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model) throws TooManyMinesException {
        //method renamed from minesweeper

        startOrUpdateGame(row, column);
        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/mark")
    public String changeMarking(Model model) {
        switchMode();
        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/new")
    public String newGame(Model model) throws TooManyMinesException {
        startNewGame();
        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/asynch")
    public String loadInAsynchMode(Model model) throws TooManyMinesException {
        startOrUpdateGame(null, null);
        prepareAsynchModel(model);
        return "minesweeperAsynch";
    }

    @RequestMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field processUserInputJson(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column) throws TooManyMinesException {
        //method renamed from minesweeper
        this.field.setJustFinished(startOrUpdateGame(row, column));
        this.field.setMarking(marking);
        return this.field;
    }

    @RequestMapping(value = "/jsonmark", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field changeMarkingJson() {
        switchMode();
        this.field.setJustFinished(false);
        this.field.setMarking(marking);
        return this.field;
    }

    @RequestMapping(value = "/jsonnew", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Field newGameJson() throws TooManyMinesException {
        startNewGame();
        this.field.setJustFinished(false);
        this.field.setMarking(marking);
        return this.field;
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

    @RequestMapping("/comment")
    public String addComment(String comment, Model model) {
        if (userController.isLogged() && userController.isUserAlreadyExists())
            commentService.addComment(new Comment(GAME, userController.getLoggedUser(), comment, new Date()));
        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/rating")
    public String addRating(int rating, Model model) {
        if (userController.isLogged() && userController.isUserAlreadyExists())
            ratingService.setRating(new Rating(GAME, userController.getLoggedUser(), rating, new Date()));
        prepareModel(model);
        return "minesweeper";
    }

    public String getCurrTime() {
        return new Date().toString();
    }

    public boolean getMarking() {
        return this.marking;
    }


    /**
     * Generates the full table with the minesweeper field.
     * (now unused, this is transformed to the template)
     *
     * @return String with HTML of the table
     */
    public String getFieldAsHtml() {

        int rowCount = this.field.getRowCount();
        int colCount = this.field.getColumnCount();

        StringBuilder sb = new StringBuilder();

        sb.append("<table class='minefield'>\n");

        for (int row = 0; row < rowCount; row++) {
            sb.append("<tr>\n");

            for (int col = 0; col < colCount; col++) {
                Tile tile = this.field.getTile(row, col);

                sb.append("<td class='" + getTileClass(tile) + "'> ");
                sb.append("<a href='/minesweeper?row=" + row + "&column=" + col + "'> ");
                sb.append("<span>" + getTileText(tile) + "</span>");
                sb.append(" </a>\n");
                sb.append(" </td>\n");

            }
            sb.append("</tr>\n");
        }


        sb.append("</table>\n");

        return sb.toString();
    }

    /**
     * Gets the text that may be displayed inside a HTML element representing 1 tile
     * Now public as it is called from the template
     *
     * @param tile - the Tile object the text is extracted from
     * @return the text that may be displayed inside a HTML element representing the Tile tile
     */
    public String getTileText(Tile tile) {
        switch (tile.getState()) {
            case CLOSED:
                return "-";
            case MARKED:
                return "M";
            case OPEN:
                if (tile instanceof Clue) {
                    return String.valueOf(((Clue) tile).getValue());
                } else {
                    return "X";
                }
            default:
                throw new IllegalArgumentException("Unsupported tile state " + tile.getState());
        }
    }

    /**
     * Gets the HTML class of the <code>td</code> element representing the tile
     * Now public as it is called from the template
     *
     * @param tile the Tile object the class is assigned to
     * @return String with the HTML class of the <code>td</code> element representing the Tile tile
     */
    public String getTileClass(Tile tile) {
        switch (tile.getState()) {
            case OPEN:
                if (tile instanceof Clue)
                    return "open" + ((Clue) tile).getValue();
                else
                    return "mine";
            case CLOSED:
                return "closed";
            case MARKED:
                return "marked";
            default:
                throw new RuntimeException("Unexpected tile state");
        }
    }

    private final int ROW_COUNT = 9;
    private final int COLUMN_COUNT = 9;
    private final int MINE_COUNT = 10;
    /**
     * Initiates the game field and other variables to the state at the start of a new game
     */
    private void startNewGame() throws TooManyMinesException {
        this.field = new Field(ROW_COUNT, COLUMN_COUNT, MINE_COUNT);
        this.isPlaying = true;
        this.marking = false;
    }

    /**
     * Updates the game field and other variables according to the move of the user
     * Also adds the score to the score table if the game just ended.
     * If the game did not start yet, starts the game.
     *
     * @param row    row of the tile on which the user clicked
     * @param column column of the tile on which the user clicked
     */
    private boolean startOrUpdateGame(Integer row, Integer column) throws TooManyMinesException {
        boolean justFinished = false;
        if (field == null) {
            startNewGame();
        }

        if (row != null && column != null) {

            if (this.marking) {
                this.field.markTile(row, column);
            } else {
                this.field.openTile(row, column);
            }


            if (this.field.getState() != GameState.PLAYING && this.isPlaying == true) { //I just won/lost
                this.isPlaying = false;
                justFinished = true;

                if (userController.isLogged() && this.field.getState() == GameState.SOLVED) {
                    Score newScore = new Score(GAME, userController.getLoggedUser(), this.field.getScore(), new Date());
                    scoreService.addScore(newScore);
                }
            }
        }
        return justFinished;
    }

    /**
     * Switches the game mode (the <code>marking</code> property) between opening and marking the tiles.
     * Applies only when the game is played.
     */
    private void switchMode() {
        if (this.field.getState() == GameState.PLAYING) {
            this.marking = !this.marking;
        }
    }

    private String getGameStatusMessage() {
        String gameStatus = "";
        if (this.field.getState() == GameState.FAILED) {
            gameStatus = "Prehral si";
        } else if (this.field.getState() == GameState.SOLVED) {
            gameStatus = "Vyhral si (skóre: " + this.field.getScore() + ")";
        } else {
            gameStatus = "Hráš a ";
            if (this.marking) {
                gameStatus += "označuješ";
            } else {
                gameStatus += "otváraš";
            }
        }
        return gameStatus;
    }

    /**
     * Fills the Spring MVC model object for the Thymeleaf template
     *
     * @param model - the Spring MVC model
     */
    private void prepareModel(Model model) {
        model.addAttribute("isPlaying", this.isPlaying);
        model.addAttribute("marking", this.marking);
        model.addAttribute("gameStatus", getGameStatusMessage());
        model.addAttribute("minesweeperField", this.field.getTiles());
        model.addAttribute("bestScores", scoreService.getBestScores(GAME));
        model.addAttribute("comments", commentService.getComments(GAME));
        model.addAttribute("rating", ratingService.getAverageRating(GAME));
    }

    private void prepareAsynchModel(Model model) {
//        model.addAttribute("comments", commentService.getComments(GAME));
        model.addAttribute("rating", ratingService.getAverageRating(GAME));
    }
}

//package sk.tsystems.gamestudio.server.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.context.WebApplicationContext;
//import sk.tsystems.gamestudio.entity.Comment;
//import sk.tsystems.gamestudio.entity.Rating;
//import sk.tsystems.gamestudio.entity.Score;
//import sk.tsystems.gamestudio.minesweeper.core.*;
//import sk.tsystems.gamestudio.service.CommentService;
//import sk.tsystems.gamestudio.service.RatingService;
//import sk.tsystems.gamestudio.service.ScoreService;
//
//import java.util.Date;
//
//@Controller
//@RequestMapping("/minesweeper")
//@Scope(WebApplicationContext.SCOPE_SESSION)
//public class MinesweeperController {
//
//    private final int ROW_COUNT = 9;
//    private final int COLUMN_COUNT = 9;
//    private final int MINE_COUNT = 10;
//    private Field field = new Field(ROW_COUNT, COLUMN_COUNT, MINE_COUNT);
//
//    private boolean marking = false;
//    private boolean play = true;
//    private boolean won = false;
//    private boolean lost = false;
//
//    private final String GAME = "minesweeper";
//    private long startTime;
//    private int score;
//
//    @Autowired
//    private ScoreService scoreService;
//    @Autowired
//    private CommentService commentService;
//    @Autowired
//    private RatingService ratingService;
//    @Autowired
//    private UserController userController;
//
//    public MinesweeperController() throws TooManyMinesException {
//    }
//
//    @RequestMapping
//    public String minesweeper(@RequestParam(required = false) Integer row, @RequestParam(required = false)Integer column,
//                              Model model){
//        if(row != null && column != null){
//
//            if (marking) {
//                field.markTile(row,column);
//            } else {
//                field.openTile(row,column);
//            }
//
//            if (!field.getState().equals(GameState.PLAYING)) {
//                if (field.getState().equals(GameState.SOLVED)) {
//                    System.out.println(System.currentTimeMillis());
//                    System.out.println(startTime);
//                    score = field.getRowCount() * field.getColumnCount() * 10 - (int) ((System.currentTimeMillis() - startTime) / 1000);
//                    if (userController.isLogged())
//                        scoreService.addScore(new Score(GAME, userController.getLoggedUser(), score, new Date()));
//                }
//                play = false;
//            }
//        } else {
//            startTime = System.currentTimeMillis();
//        }
//
//        prepareModel(model);
//        return "minesweeper";
//    }
//
//    @RequestMapping("/mark")
//    public String changeMarking(Model model){
//        marking = !marking;
//        prepareModel(model);
//        return "minesweeper";
//    }
//
//    @RequestMapping("/new")
//    public String newGame(Model model) throws TooManyMinesException {
//        field = new Field(ROW_COUNT, COLUMN_COUNT, MINE_COUNT);
//        play = true;
//        won = false;
//        lost = false;
//        prepareModel(model);
//        startTime = System.currentTimeMillis();
//        return "minesweeper";
//    }
//
//    @RequestMapping("/comment")
//    public String addComment(String comment, Model model) {
//        if (userController.isLogged())
//            commentService.addComment(new Comment(GAME, userController.getLoggedUser(), comment, new Date()));
//        prepareModel(model);
//        return "minesweeper";
//    }
//
//    @RequestMapping("/rating")
//    public String addRating(int rating, Model model) {
//        if (userController.isLogged())
//            ratingService.setRating(new Rating(GAME, userController.getLoggedUser(), rating, new Date()));
//        prepareModel(model);
//        return "minesweeper";
//    }
//
//    public String getCurrTime(){
//        return new Date().toString();
//    }
//
//    public boolean getMarking(){
//        return marking;
//    }
//
//    public boolean isPlay() {
//        return play;
//    }
//
//    public boolean isWon() {
//        return won;
//    }
//
//    public boolean isLost() {
//        return lost;
//    }
//
//    public String getFieldAsHtml(){
//        int rowCount = field.getRowCount();
//        int colCount = field.getColumnCount();
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("<table class='minefield'>\n");
//        for (int row = 0; row<rowCount;row++){
//            sb.append("<tr>\n");
//            for (int col = 0; col<colCount;col++){
//                Tile tile = field.getTile(row,col);
//
//                sb.append("<td class='" + getTileClass(tile) + "'> ");
//                sb.append("<a href='/minesweeper/?row="+row+"&column="+col+"'> ");
//                sb.append("<span>" + getTileText(tile) + "</span>");
//                sb.append(" </a>\n");
//                sb.append(" </td>\n");
//            }
//            sb.append("</tr>\n");
//        }
//        sb.append("</table>\n");
//        return sb.toString();
//    }
//
//    public String getTileText(Tile tile){
//        switch (tile.getState()){
//            case CLOSED:
//                return "-";
//            case MARKED:
//                return "M";
//            case OPEN:
//                if (tile instanceof Clue) {
//                    return String.valueOf(((Clue) tile).getValue());
//                } else {
//                    return "X";
//                }
//            default:
//                throw new IllegalArgumentException("Unsupported tile state " + tile.getState());
//        }
//    }
//
//    public String getTileClass(Tile tile) {
//        switch (tile.getState()) {
//            case OPEN:
//                if (tile instanceof Clue)
//                    return "open" + ((Clue) tile).getValue();
//                else
//                    return "mine";
//            case CLOSED:
//                return "closed";
//            case MARKED:
//                return "marked";
//            default:
//                throw new RuntimeException("Unexpected tile state");
//        }
//    }
//
//    private void prepareModel(Model model) {
//        model.addAttribute("minesweeperField", field.getTiles());
//
//        model.addAttribute("bestScores", scoreService.getBestScores(GAME));
//        model.addAttribute("comments", commentService.getComments(GAME));
//        model.addAttribute("rating", ratingService.getAverageRating(GAME));
//
//        if (field.getState().equals(GameState.PLAYING))
//            model.addAttribute("gameState", "Hrá sa.");
//        else if (field.getState().equals(GameState.SOLVED)) {
//            model.addAttribute("gameState", "Vyhral si. Tvoje skóre je: " + score);
//        } else
//            model.addAttribute("gameState", "Prehral si.");
//    }
//}
