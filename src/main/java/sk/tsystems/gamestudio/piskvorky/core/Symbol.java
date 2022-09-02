package sk.tsystems.gamestudio.piskvorky.core;

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

}
