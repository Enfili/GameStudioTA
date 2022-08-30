package sk.tsystems.gamestudio.piskvorky;

import java.util.Random;

public class IfElseAI extends Board{

    public IfElseAI(int rows, int columns) {
        super(rows, columns);
    }

    public int[] findMove() {
        int[] move = new int[2];

        Random r = new Random();
        do {
            move[0] = r.nextInt(getBoard().length);
            move[1] = r.nextInt(getBoard()[0].length);
        } while (!validMove(move[0], move[1]));

        return move;
    }
}
