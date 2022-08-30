package sk.tsystems.gamestudio.piskvorky;

public class Symbol {

    public enum State {
        EMPTY,
        CIRCLE,
        CROSS;
    }

    private State state;
    private boolean crossedOut;

    public Symbol() {
        this.state = State.EMPTY;
        crossedOut = false;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isCrossedOut() {
        return crossedOut;
    }

    public void setCrossedOut(boolean crossedOut) {
        this.crossedOut = crossedOut;
    }
}
