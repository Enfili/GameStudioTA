package sk.tsystems.gamestudio.piskvorky.core;

import java.util.*;

public class Board {

    private Symbol[][] board;
    private int rowCount;
    private int colCount;
    private boolean playing;
    private int move;
    private int[] score;
    // maps to remember symbols (inRows) that are in given direction on board
    protected Map<String, ArrayList<InRow>> symbolsInRow;
    private Map<String, ArrayList<InRow>> crossedSymbols;

    protected final String[] DIRECTIONS = {"down", "right", "upRight", "downRight"};

    public Board(int rows, int columns) {
        rowCount = rows;
        colCount = columns;
        this.board = new Symbol[rows][columns];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Symbol();
            }
        }
        score = new int[]{0, 0};
        playing = true;
        move = 1;

        crossedSymbols = new HashMap<>();
        for (String dir: DIRECTIONS) {
            crossedSymbols.put(dir, new ArrayList<>());
        }

        symbolsInRow = new HashMap<>();
        for (String dir: DIRECTIONS) {
            symbolsInRow.put(dir, new ArrayList<>());
        }
    }

    public void drawSymbol(int row, int column) {
        if (move % 2 == 1)
            this.board[row][column].setState(Symbol.State.CIRCLE);
        else if (move % 2 == 0)
            this.board[row][column].setState(Symbol.State.CROSS);

        move++;
        if (move > rowCount * colCount)
            playing = false;
    }

    public boolean validMove(int row, int col) {
        if (!onBoard(row, col))
            return false;
        if (!board[row][col].getState().equals(Symbol.State.EMPTY))
            return false;
        return true;
    }

    private boolean onBoard(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return false;
        return true;
    }

    public void updateGame() {
        clearSymbolsInRow();
        inRow(5, Symbol.State.CIRCLE);
        inRow(5, Symbol.State.CROSS);
        inRow(4, Symbol.State.CIRCLE);
        inRow(4, Symbol.State.CROSS);
        inRow(3, Symbol.State.CIRCLE);
        inRow(3, Symbol.State.CROSS);
    }

    private void inRow(int nbOfNeighbours, Symbol.State state) {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                for (String dir: DIRECTIONS) {
                    int inRow = 0;
                    int x = row;
                    int y = col;

                    if (dir.equals("down")) {
                        while (condition(x, y, nbOfNeighbours, dir, inRow, state)) {
                            inRow++;
                            y++;
                        }
                    } else if (dir.equals("right")) {
                        while (condition(x, y, nbOfNeighbours, dir, inRow, state)) {
                            inRow++;
                            x++;
                        }
                    } else if (dir.equals("upRight")) {
                        while (condition(x, y, nbOfNeighbours, dir, inRow, state)) {
                            inRow++;
                            x++;
                            y--;
                        }
                    } else {
                        while (condition(x, y, nbOfNeighbours, dir, inRow, state)) {
                            inRow++;
                            x++;
                            y++;
                        }
                    }

                    if (inRow == nbOfNeighbours) {
                        if (nbOfNeighbours == 5) {
                            crossedSymbols.get(dir).add(new InRow(new int[]{row, col}, nbOfNeighbours, dir, state));
                            if (state == Symbol.State.CIRCLE)
                                score[0] += 1;
                            else
                                score[1] += 1;
                        } else {
                            symbolsInRow.get(dir).add(new InRow(new int[]{row, col}, nbOfNeighbours, dir, state));
                        }
                    }
                }
            }
        }
    }

    private boolean condition(int x, int y, int nbOfNeighbours, String dir, int inRow, Symbol.State state) {
        return onBoard(x, y) && board[x][y].getState() == state && inRow < nbOfNeighbours && !isAlreadyInMap(x, y, dir);
    }

    private boolean isAlreadyInMap(int x, int y, String dir) {
        for (InRow inRow: crossedSymbols.get(dir)) {
            for (int[] positions: inRow.getPositions()) {
                if (x == positions[0] && y == positions[1])
                    return true;
            }
        }
        for (InRow inRow: symbolsInRow.get(dir)) {
            for (int[] positions: inRow.getPositions()) {
                if (x == positions[0] && y == positions[1])
                    return true;
            }
        }
        return false;
    }

    private void clearSymbolsInRow() {
        symbolsInRow.clear();
        for (String dir: DIRECTIONS) {
            symbolsInRow.put(dir, new ArrayList<>());
        }
    }

    public Symbol[][] getBoard() {
        return board;
    }

    public int[] getScore() {
        return score;
    }

    public Map<String, ArrayList<InRow>> getCrossedSymbols() {
        return crossedSymbols;
    }

    public boolean isPlaying() {
        return playing;
    }
}
