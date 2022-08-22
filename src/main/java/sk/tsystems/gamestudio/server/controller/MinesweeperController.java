package sk.tsystems.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.minesweeper.core.Clue;
import sk.tsystems.gamestudio.minesweeper.core.Field;
import sk.tsystems.gamestudio.minesweeper.core.Tile;
import sk.tsystems.gamestudio.minesweeper.core.TooManyMinesException;

import java.util.Date;

@Controller
@RequestMapping("/minesweeper")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesweeperController {

    private Field field = new Field(9,9,10);

    private boolean marking = false;

    public MinesweeperController() throws TooManyMinesException {
    }

    @RequestMapping
    public String minesweeper(@RequestParam(required = false) Integer row, @RequestParam(required = false)Integer column,
                              Model model){

        if(row != null && column != null){

            if(marking){
                field.markTile(row,column);
            }else{
                field.openTile(row,column);
            }
        }

        prepareModel(model);
        return "minesweeper";
    }

    @RequestMapping("/mark")
    public String changeMarking(){
        marking = !marking;
        return "minesweeper";
    }

    @RequestMapping("/new")
    public String newGame() throws TooManyMinesException {
        field = new Field(9,9,10);
        return "minesweeper";
    }

    public String getCurrTime(){
        return new Date().toString();
    }

    public boolean getMarking(){
        return marking;
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
        model.addAttribute("message", "Sprava z modelu.");
        model.addAttribute("minesweeperField", field.getTiles());
    }
}
