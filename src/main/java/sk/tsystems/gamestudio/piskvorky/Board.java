package sk.tsystems.gamestudio.piskvorky;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Board {

    private Symbol[][] board;
    private int move;

    public Board(int rows, int columns) {
        this.board = new Symbol[rows][columns];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Symbol();
            }
        }
        move = 1;
    }

    public void newGame(int rows, int columns) {
        new Board(rows, columns);
    }

    public void drawSymbol(int row, int column) {
        if (move % 2 == 1)
            this.board[row][column].setState(Symbol.State.CIRCLE);
        else if (move % 2 == 0)
            this.board[row][column].setState(Symbol.State.CROSS);

        move++;
    }

    public void inRow(int nbOfNeighbours) {

    }

    public Symbol[][] getBoard() {
        return board;
    }
}
