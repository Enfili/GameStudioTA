package sk.tsystems.gamestudio.minesweeper;

import sk.tsystems.gamestudio.minesweeper.core.Field;
import sk.tsystems.gamestudio.minesweeper.core.GameState;
import sk.tsystems.gamestudio.minesweeper.core.TooManyMinesException;

public interface UserInterface {
    GameState newGameStarted(Field field) throws TooManyMinesException;

    void update() throws TooManyMinesException;

    void play() throws TooManyMinesException;
}
