package sk.tsystems.gamestudio.piskvorky;

import java.util.Random;

public class IfElseAI extends Board{

    public IfElseAI(int rows, int columns) {
        super(rows, columns);
    }

    public int[] findMove() {
        int[] move = new int[2];

        move = findMoveInRow(4);
        if (move != null)
            return move;
        move = findMoveInRow(3);
        if (move != null)
            return move;

        Random r = new Random();
        do {
            move = new int[] {r.nextInt(getBoard().length), r.nextInt(getBoard()[0].length)};
        } while (!validMove(move[0], move[1]));

        return move;
    }

    private int[] findMoveInRow(int neighbours) {
        for (String dir: DIRECTIONS) {
            for (InRow inRow : symbolsInRow.get(dir)) {
                if (inRow.getNumber() == neighbours) {
                    int[] firstSymbolInRow = inRow.getPosition();
                    if (dir.equals("down")) {
                        if (validMove(firstSymbolInRow[0], firstSymbolInRow[1] - 1)) {
                            return new int[] {firstSymbolInRow[0], firstSymbolInRow[1] - 1};
                        } else if (validMove(firstSymbolInRow[0], firstSymbolInRow[1] + neighbours)) {
                            return new int[] {firstSymbolInRow[0], firstSymbolInRow[1] + neighbours};
                        }
                    } else if (dir.equals("right")) {
                        if (validMove(firstSymbolInRow[0] - 1, firstSymbolInRow[1])) {
                            return new int[] {firstSymbolInRow[0] - 1, firstSymbolInRow[1]};
                        } else if (validMove(firstSymbolInRow[0] + neighbours, firstSymbolInRow[1])) {
                            return new int[] {firstSymbolInRow[0] + neighbours, firstSymbolInRow[1]};
                        }
                    } else if (dir.equals("upRight")) {
                        if (validMove(firstSymbolInRow[0] - 1, firstSymbolInRow[1] + 1)) {
                            return new int[] {firstSymbolInRow[0] - 1, firstSymbolInRow[1] + 1};
                        } else if (validMove(firstSymbolInRow[0] + neighbours, firstSymbolInRow[1] - neighbours)) {
                            return new int[] {firstSymbolInRow[0] + neighbours, firstSymbolInRow[1] - neighbours};
                        }
                    } else {
                        if (validMove(firstSymbolInRow[0] - 1, firstSymbolInRow[1] - 1)) {
                            return new int[] {firstSymbolInRow[0] - 1, firstSymbolInRow[1] - 1};
                        } else if (validMove(firstSymbolInRow[0] + neighbours, firstSymbolInRow[1] + neighbours)) {
                            return new int[] {firstSymbolInRow[0] + neighbours, firstSymbolInRow[1] + neighbours};
                        }
                    }
                }
            }
        }

        return null;
    }
}
