package sk.tsystems.gamestudio.minesweeper.core;

/**
 * Mine tile.
 */
public class Mine extends Tile {

    @Override
    public String toString() {
        if (getState() == State.OPEN)
            return "*";
        return super.toString();
    }
}
