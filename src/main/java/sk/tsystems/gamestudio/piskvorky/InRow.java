package sk.tsystems.gamestudio.piskvorky;

import java.util.ArrayList;
import java.util.List;

public class InRow {
    private int number;
    private String direction;
    private List<int[]> positions;
    private Symbol.State state;

    public InRow(int number, String direction, Symbol.State state) {
        this.number = number;
        this.direction = direction;
        this.state = state;
        positions = findPositions();
    }

    //todo: finish this method
    private List<int[]> findPositions() {
        List<int[]> positions = new ArrayList<>();

        return positions;
    }
}
