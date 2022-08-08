package sk.tsystems.gamestudio.minesweeper.core;

public class TooManyMinesException extends Exception{
    public TooManyMinesException(String message) {
        super(message);
    }
}
