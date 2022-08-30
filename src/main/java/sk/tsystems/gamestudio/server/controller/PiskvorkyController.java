package sk.tsystems.gamestudio.server.controller;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import sk.tsystems.gamestudio.minesweeper.core.Field;
import sk.tsystems.gamestudio.minesweeper.core.TooManyMinesException;
import sk.tsystems.gamestudio.piskvorky.Board;
import sk.tsystems.gamestudio.piskvorky.IfElseAI;
import sk.tsystems.gamestudio.piskvorky.InRow;
import sk.tsystems.gamestudio.piskvorky.Symbol;

import java.util.ArrayList;

@Controller
@RequestMapping("/piskvorky")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PiskvorkyController {

    private IfElseAI board;
    private final int NUMBER_OF_ROWS = 12;
    private final int NUMBER_OF_COLUMNS = 12;

    private int move;
    private int[] score = new int[2];

    @RequestMapping
    public String piskvorky() {
        if (board == null) {
            board = new IfElseAI(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
            move = 1;
            score[0] = 0;
            score[1] = 0;
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
        board.drawSymbol(row, column, move);
        move++;
        board.updateGame();
    }

    @RequestMapping(value = "/jsonnew", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public IfElseAI newGame() {
        board = new IfElseAI(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        move = 1;
        board.updateGame();
        return this.board;
    }

    public String getCellText(Symbol symbol) {
        if (symbol.getState().equals(Symbol.State.EMPTY))
            return " ";
        else if (symbol.getState().equals(Symbol.State.CIRCLE))
            return "O";
        else
            return "X";
    }
}
