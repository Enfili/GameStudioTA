package sk.tsystems.gamestudio.piskvorky;

import java.util.ArrayList;
import java.util.List;

public class InRow {

    private int[] position;
    private int number;
    private String direction;
    private Symbol.State state;
    private ArrayList<int[]> positions;

    public InRow(int[] position, int number, String direction, Symbol.State state) {
        this.position = position;
        this.number = number;
        this.direction = direction;
        this.state = state;
        this.positions = findPositions();
    }

    private ArrayList<int[]> findPositions() {
        ArrayList<int[]> positions = new ArrayList<>();
        int x = position[0];
        int y = position[1];
        for (int i = 0; i < 5; i++) {
            if (direction.equals("right")) {
                positions.add(new int[] {x, y});
                x++;
            } else if (direction.equals("down")) {
                positions.add(new int[] {x, y});
                y++;
            } else if (direction.equals("upRight")) {
                positions.add(new int[] {x, y});
                x++;
                y--;
            } else {
                positions.add(new int[] {x, y});
                x++;
                y++;
            }
        }
        return positions;
    }

    public int getNumber() {
        return number;
    }

    public int[] getPosition() {
        return position;
    }

    public String getDirection() {
        return direction;
    }

    public Symbol.State getState() {
        return state;
    }

    public ArrayList<int[]> getPositions() {
        return positions;
    }
}
