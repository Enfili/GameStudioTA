package sk.tsystems.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.piskvorky.Board;
import sk.tsystems.gamestudio.piskvorky.Symbol;

@Controller
@RequestMapping("/piskvorky")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PiskvorkyController {

    private Board board;
    private final int NUMBER_OF_ROWS = 12;
    private final int NUMBER_OF_COLUMNS = 12;

    @RequestMapping
    public String piskvorky(Model model) {

        board = new Board(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);

        prepareModel(model);
        return "piskvorky";
    }

    public String getCellText(Symbol symbol) {
        if (symbol.getState().equals(Symbol.State.EMPTY))
            return " ";
        else if (symbol.getState().equals(Symbol.State.CIRCLE))
            return "C";
        else
            return "X";
    }

    private void prepareModel(Model model) {
        model.addAttribute("board", this.board.getBoard());
    }

}
