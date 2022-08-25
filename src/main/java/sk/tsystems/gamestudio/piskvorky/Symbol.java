package sk.tsystems.gamestudio.piskvorky;

public class Symbol {

    public enum State {
        EMPTY,
        CIRCLE,
        CROSS;
    }

    private State state;

    public Symbol() {
        this.state = State.EMPTY;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
