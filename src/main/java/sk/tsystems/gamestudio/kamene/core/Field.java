package sk.tsystems.gamestudio.kamene.core;

import sk.tsystems.gamestudio.kamene.userInterface.MoveOutOfFieldException;

import java.io.Serializable;
import java.util.Random;

public class Field implements Serializable {
    private static final long serialVersionUID = 2L;
    private String[] moves = {"w", "a", "s", "d"};
    private final int rowCount;
    private final int columnCount;
    private final Stone[][] stones;
    private int emptyStoneRow;
    private int emptyStoneColumn;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        stones = new Stone[rowCount][columnCount];
        emptyStoneRow = rowCount - 1;
        emptyStoneColumn = columnCount - 1;
        generate();
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Stone[][] getStones() {
        return stones;
    }

    public int getEmptyStoneRow() {
        return emptyStoneRow;
    }

    public void setEmptyStoneRow(int emptyStoneRow) {
        this.emptyStoneRow = emptyStoneRow;
    }

    public int getEmptyStoneColumn() {
        return emptyStoneColumn;
    }

    public void setEmptyStoneColumn(int emptyStoneColumn) {
        this.emptyStoneColumn = emptyStoneColumn;
    }

    private void generate() {
        int value = 1;
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones[i].length; j++) {
                if (i != stones.length - 1 || j != stones[i].length - 1) {
                    stones[i][j] = new Stone(value, i, j);
                    value++;
                } else {
                    stones[i][j] = null;
                }
            }
        }
    }

    public boolean isSolved() {
        // empty stone has to be placed last on the field
        if (stones[rowCount - 1][columnCount - 1] != null)
            return false;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount - 1; j++) {
                if (i == rowCount - 1 && j + 1 == columnCount - 1)
                    return true;
                if (stones[i][j + 1] != null && stones[i][j].getValue() > stones[i][j +  1].getValue())
                    return false;
            }
        }
        return true;
    }

    public void shuffle(int nbOfShifts) {
        Random r = new Random();
        for (int i = 0; i < nbOfShifts; i++) {
            try {
                shiftStone(moves[r.nextInt(4)]);
            } catch (MoveOutOfFieldException e) {
                // intentionally left empty
            }
        }
    }

    public void shiftStone(String shift) throws MoveOutOfFieldException {
        int emptyRow = this.getEmptyStoneRow();
        int emptyColumn = this.getEmptyStoneColumn();
        switch (shift) {
            case "w":
            case "up":
                if (emptyRow + 1 < this.getRowCount()) {
                    swapStones(this.getStones()[emptyRow + 1][emptyColumn]);
                } else {
                    throw new MoveOutOfFieldException("Out of this field move.");
                }
                break;
            case "a":
            case "left":
                if (emptyColumn + 1 < this.getColumnCount()) {
                    swapStones(this.getStones()[emptyRow][emptyColumn + 1]);
                } else {
                    throw new MoveOutOfFieldException("Out of this field move.");
                }
                break;
            case "s":
            case "down":
                if (emptyRow - 1 >= 0) {
                    swapStones(this.getStones()[emptyRow - 1][emptyColumn]);
                } else {
                    throw new MoveOutOfFieldException("Out of this field move.");
                }
                break;
            case "d":
            case "right":
                if (emptyColumn - 1 >= 0) {
                    swapStones(this.getStones()[emptyRow][emptyColumn - 1]);
                } else {
                    throw new MoveOutOfFieldException("Out of this field move.");
                }
                break;
        }
    }

    private void swapStones(Stone stone) {
        this.getStones()[stone.getRow()][stone.getColumn()] = null;
        this.getStones()[this.getEmptyStoneRow()][this.getEmptyStoneColumn()] = stone;
        int newEmptyRow = stone.getRow();
        int newEmptyColumn = stone.getColumn();
        stone.setRow(this.getEmptyStoneRow());
        stone.setColumn(this.getEmptyStoneColumn());
        this.setEmptyStoneRow(newEmptyRow);
        this.setEmptyStoneColumn(newEmptyColumn);
    }
}
