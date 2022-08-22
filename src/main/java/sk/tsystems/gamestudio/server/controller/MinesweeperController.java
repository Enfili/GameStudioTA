package sk.tsystems.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.minesweeper.core.*;
import sk.tsystems.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/minesweeper")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesweeperController {

    private Field field = new Field(9,9,0);

    private boolean marking = false;
    private boolean play = true;
    private boolean won = false;
    private boolean lost = false;

    private String GAME = "minesweeper";
    private long startTime;
    private int score;

    @Autowired
    private ScoreService scoreService;

    public MinesweeperController() throws TooManyMinesException {
    }

    @RequestMapping
    public String minesweeper(@RequestParam(required = false) Integer row, @RequestParam(required = false)Integer column,
                              Model model){
        if(row != null && column != null){

            if (marking) {
                field.markTile(row,column);
            } else {
                field.openTile(row,column);
            }

            if (!field.getState().equals(GameState.PLAYING)) {
                if (field.getState().equals(GameState.SOLVED)) {
                    System.out.println(System.currentTimeMillis());
                    System.out.println(startTime);
                    score = field.getRowCount() * field.getColumnCount() * 10 - (int) (System.currentTimeMillis() - startTime);
                    scoreService.addScore(new Score(GAME, "anonym", score, new Date()));
                }
                play = false;
            }
        } else {
            startTime = System.currentTimeMillis();
        }

        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/mark")
    public String changeMarking(Model model){
        marking = !marking;
        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/new")
    public String newGame(Model model) throws TooManyMinesException {
        field = new Field(9,9,10);
        play = true;
        won = false;
        lost = false;
        prepareModel(model);
        return "minesweeper";
    }

    public String getCurrTime(){
        return new Date().toString();
    }

    public boolean getMarking(){
        return marking;
    }

    public boolean isPlay() {
        System.out.println(play);
        return play;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isLost() {
        return lost;
    }

    public String getFieldAsHtml(){
        int rowCount = field.getRowCount();
        int colCount = field.getColumnCount();

        StringBuilder sb = new StringBuilder();
        sb.append("<table class='minefield'>\n");
        for (int row = 0; row<rowCount;row++){
            sb.append("<tr>\n");
            for (int col = 0; col<colCount;col++){
                Tile tile = field.getTile(row,col);

                sb.append("<td class='" + getTileClass(tile) + "'> ");
                sb.append("<a href='/minesweeper/?row="+row+"&column="+col+"'> ");
                sb.append("<span>" + getTileText(tile) + "</span>");
                sb.append(" </a>\n");
                sb.append(" </td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    public String getTileText(Tile tile){
        switch (tile.getState()){
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

    private void prepareModel(Model model) {
        model.addAttribute("minesweeperField", field.getTiles());

        model.addAttribute("bestScores", scoreService.getBestScores(GAME));

        if (field.getState().equals(GameState.PLAYING))
            model.addAttribute("gameState", "Hrá sa.");
        else if (field.getState().equals(GameState.SOLVED)) {
            model.addAttribute("gameState", "Vyhral si. Tvoje skóre je: " + score);
        } else
            model.addAttribute("gameState", "Prehral si.");
    }
}
