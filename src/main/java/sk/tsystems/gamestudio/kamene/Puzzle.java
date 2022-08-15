package sk.tsystems.gamestudio.kamene;

import sk.tsystems.gamestudio.kamene.core.Field;
import sk.tsystems.gamestudio.kamene.userInterface.ConsoleUI;
import sk.tsystems.gamestudio.service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Puzzle {
    ConsoleUI ui;
    Field field;

    private final String GAME_NAME = "kamene";

    public Puzzle() {
        ui = new ConsoleUI();
        ui.play();
    }

    public static void main(String[] args) {
        new Puzzle();
    }
}
