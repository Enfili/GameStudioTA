package sk.tsystems.gamestudio.minesweeper;

import sk.tsystems.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.minesweeper.core.TooManyMinesException;

/**
 * Main application class.
 */
public class Minesweeper {
    private static Minesweeper instance;

    public static Minesweeper getInstance() throws TooManyMinesException {
        if (instance == null)
            new Minesweeper();
        return instance;
    }
 
    /**
     * Constructor.
     */
    private Minesweeper() throws TooManyMinesException {
        instance = this;

        final UserInterface userInterface = new ConsoleUI();
        userInterface.play();
    }

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) throws TooManyMinesException {
//        System.out.println("Hello user with name: " + System.getProperty("user.name"));
        new Minesweeper();
    }
}
