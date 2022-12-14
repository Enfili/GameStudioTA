package sk.tsystems.gamestudio.minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tsystems.gamestudio.entity.*;
import sk.tsystems.gamestudio.minesweeper.UserInterface;
import sk.tsystems.gamestudio.minesweeper.core.Field;
import sk.tsystems.gamestudio.minesweeper.core.GameState;
import sk.tsystems.gamestudio.minesweeper.core.TooManyMinesException;
import sk.tsystems.gamestudio.service.*;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    public static final int LETTER_ASCII = 65;
    public static final Pattern PATTERN = Pattern.compile("([moMO])([a-zA-Z])(-?\\d+)");
    /** Playing field. */
    private Field field;

    private String format = "%3s";

    // originally in Minesweeper.java
    private long startMillis;
    private Settings setting;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private OccupationService occupationService;
    @Autowired
    private PlayerService playerService;

    private final String GAME_NAME = "minesweeper";

    /** Input reader. */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Reads line of text from the reader.
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Starts the game.
     * @param field field of mines and clues
     */
    @Override
    public GameState newGameStarted(Field field) throws TooManyMinesException {
        this.field = field;
        this.format = "%" + (String.valueOf(field.getColumnCount()).length() + 1) + "s";

        System.out.println("Chce?? si vybra?? obtia??nos???");
        System.out.println("(0) NIE (1) BEGINNER, (2) INTERMEDIATE, (3) EXPERT");
        String difficulty = readLine();
        if (difficulty != null && !difficulty.equals("")) {
            try {
                int level = Integer.parseInt(difficulty);
                if (level != 0) {
                    Settings s = null;
                    switch (level) {
                        case 1 -> s = Settings.BEGINNER;
                        case 2 -> s = Settings.INTERMEDIATE;
                        case 3 -> s = Settings.EXPERT;
                    }

                    if (s != null) {
                        this.setting = s;
                        this.setting.save();
//                        Minesweeper.getInstance().setSetting(s);
//                        setSetting(s);
//                        this.field = new Field(s.getRowCount(), s.getColumnCount(), s.getMineCount());
                    }
                }
            } catch (NumberFormatException e) {
                // empty on purpose
            }
        }

        update();
        do {
            processInput();
            update();
            if ((this.field.getState() == GameState.SOLVED)) {
                System.out.println("Si v????az!");
                break;
//                System.exit(0);
            } else if ((this.field.getState() == GameState.FAILED)) {
                System.out.println("Prehral si a mal by si sa hanbi??!");
                break;
//                System.exit(0);
            }
        } while(true);
        return this.field.getState();
    }
    
    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        int columnCount = field.getColumnCount();
        int rowCount = field.getRowCount();

        System.out.println("Remaining number of mines: " + field.getRemainingMineCount());

        System.out.print("  ");
        for (int i = 0; i < columnCount; i++)
            System.out.printf("%3d", i);
        System.out.println();
        for (int i = 0; i < rowCount; i++) {
            System.out.printf("%2c", (LETTER_ASCII + i));
            for (int j = 0; j < columnCount; j++) {
                System.out.printf(format, field.getTile(i, j));
            }
            System.out.println();
        }

        Long curTime = System.currentTimeMillis();
        System.out.printf("Hr???? u?? %d sek??nd.%n", ((curTime - startMillis) / 1000));
//        System.out.printf("Hr???? u?? %d sek??nd.%n", Minesweeper.getInstance().getPlayingSeconds(curTime));
//        System.out.printf("Hr???? u?? %d sek??nd.%n", getPlayingSeconds(curTime));
    }

    @Override
    public void play() throws TooManyMinesException {
        String playerName = username();
        handlePlayer(playerName);

        setting = Settings.load();
        Field field = null;
        try {
            field = new Field(setting.getRowCount(), setting.getColumnCount(), setting.getMineCount());
        } catch (TooManyMinesException e) {
            System.out.println(e.getMessage());
        }

        startMillis = System.currentTimeMillis();
        GameState gs = newGameStarted(field);

        Long endMillis = System.currentTimeMillis();
        if (GameState.SOLVED == gs) {
            int score = field.getRowCount() * field.getColumnCount() * 10 - getPlayingSeconds(endMillis);
            System.out.println(playerName + " vyhral si so sk??re: " + score);
            scoreService.addScore(new Score(GAME_NAME, playerName, score, Date.from(Instant.now())));
        } else {
            System.out.println(playerName + " prehral si so sk??re: " + 0);
            scoreService.addScore(new Score(GAME_NAME, playerName, 0, Date.from(Instant.now())));
        }
        scoreService.getBestScores(GAME_NAME).forEach(n -> System.out.println(n.getGame() + " " + n.getUsername() + " " + n.getPoints() + " " + n.getPlayedOn()));

        handleComments(playerName);
        handleRating(playerName);
    }

    private void handlePlayer(String playerName) {
        try {
            Player player = playerService.getPlayerByUserName(playerName);
            if (player == null) {
                addPlayerToDatabase(playerName);
            }
        } catch (GameStudioException e) {
            System.out.println("Chyba nastala pri pr??ci s datab??zou. (" + e.getMessage() + ")");
        }
    }

    private void addPlayerToDatabase(String username) {
        System.out.println("Pridaj nov??ho pou????vate??a.");
        try {
            System.out.println("Ak?? je tvoje cel?? meno?");
            String fullName = readLine();
            int selfEvaluation = selfEvaluate();
            Country country = chooseCountry();
            Occupation occupation = chooseOccupation();
            Player player = new Player(username, fullName, selfEvaluation, country, occupation);
            playerService.addPlayer(player);
            System.out.println("Nov?? hr???? pridan??: ");
            System.out.println(player);
        } catch (GameStudioException e) {
            System.out.println("Chyba nastala pri pr??ci s datab??zou. (" + e.getMessage() + ")");
        }
    }

    private Country chooseCountry() {
        System.out.println("V akej krajine ??ije???");
        List<Country> countries = countryService.getCountries();
        int choice = 0;
        try {
            int tries = 5;
            while((choice < 1 || choice > countries.size()) && tries > 0) {
                System.out.println("Vyber si krajinu zo zoznamu. Ak sa tvoja krajina nenach??dza v zozname, tak pridaj nov?? (0). M???? " + tries + "pokusov.");
                int nb = 1;
                for (Country country : countries) {
                    System.out.println("(" + nb + ")" + country.toString());
                    nb++;
                }
                try {
                    choice = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                    tries--;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (choice == 0) {
                    addNewCountry();
                    choice = countries.size();
                    break;
                }
            }
        } catch (GameStudioException e) {
            System.out.println("Chyba nastala pri pr??ci s datab??zou. (" + e.getMessage() + ")");
        }
        return countryService.getCountries().get(choice - 1);
    }

    private void addNewCountry() {
        System.out.println("Zadaj n??zov krajiny, v ktorej ??ije??.");
        countryService.addCountry(new Country(readLine()));
    }

    private Occupation chooseOccupation() {
        System.out.println("Ak?? je tvoje povolanie. Vyber si z ponuky.");
        List<Occupation> occupationList = occupationService.getOccupations();
        int choice = 0;
        try {
            int tries = 5;
            while((choice < 1 || choice > occupationList.size()) && tries > 0) {
                System.out.println("Vyber si povolanie zo zoznamu. Zost??va ti " + tries + "pokusov.");
                int nb = 1;
                for (Occupation occupation : occupationList) {
                    System.out.println("(" + nb + ")" + occupation.toString());
                    nb++;
                }
                try {
                    choice = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                    tries--;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (GameStudioException e) {
            System.out.println("Chyba nastala pri pr??ci s datab??zou. (" + e.getMessage() + ")");
        }
        return occupationService.getOccupations().get(choice - 1);
    }

    private int selfEvaluate() {
        int selfEvaluation = 0;
        int tries = 5;
        System.out.println("Ako by si sa s??m ohodnotil? (Od 1 po 10.)");
        while ((selfEvaluation < 1 || selfEvaluation > 10) && tries > 0) {
            selfEvaluation = Integer.parseInt(readLine());
        }
        return selfEvaluation;
    }

    private void choosePlayer(List<Player> players) {
        int choice = 0;
        int tries = 5;
        while((choice < 1 || choice > players.size()) && tries > 0) {
            System.out.println("Vyber si hr????a zo zoznamu. M???? " + tries + "pokusov.");
            int nb = 1;
            for (Player player : players) {
                System.out.println("(" + nb + ") " + player.toString());
                nb++;
            }
            try {
                choice = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
                tries--;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String username() {
        StringBuilder playerName = new StringBuilder();
        try {
            while (playerName.isEmpty() || playerName.length() > 64) {
                if (!playerName.isEmpty()) {
                    playerName.delete(0, playerName.length());
                }
                System.out.println("Ak?? je Va??e meno, z??hadn?? hr?????");
                playerName.append(new BufferedReader(new InputStreamReader(System.in)).readLine());
            }
            return playerName.toString();
        } catch (IOException e) {
            System.out.println("Nespr??vny vstup! (" + e.getMessage() + ")");
        }
        return null;
    }

    private void handleRating(String playerName) {
        int rating = 0;
        try {
            while (rating < 1 || rating > 5) {
                System.out.println("Ak?? je tvoje hodnotenie tejto ??arovnej hry? (Od 1 do 5.)");
                rating = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
            }
            ratingService.setRating(new Rating(GAME_NAME, playerName, rating, Date.from(Instant.now())));
            System.out.println("Priemern?? hodnotenie hry: " + ratingService.getAverageRating(GAME_NAME));
        } catch (IOException e) {
            System.out.println("Nespr??vny vstup! (" + e.getMessage() + ")");
        } catch (GameStudioException e) {
            System.out.println("Nepodarilo sa nastavi?? rating v datab??ze. (" + e.getMessage() + ")");
        }
    }

    private void handleComments(String playerName) {
        StringBuilder comment = new StringBuilder();
        try {
            while (comment.isEmpty() || comment.length() > 1000) {
                if (!comment.isEmpty()) {
                    comment.delete(0, comment.length());
                }
                System.out.println("\nAk?? je tvoj koment??r?");
                comment.append(new BufferedReader(new InputStreamReader(System.in)).readLine());
            }
            commentService.addComment(new Comment(GAME_NAME, playerName, comment.toString(), Date.from(Instant.now())));
            commentService.getComments(GAME_NAME).forEach(n -> System.out.println(n.getCommentedOn() + ": " + n.getComment() + "\n" + n.getUsername()));
        } catch (IOException e) {
            System.out.println("Nespr??vny vstup! (" + e.getMessage() + ")");
        } catch (GameStudioException e) {
            System.out.println("Nepodarilo sa prida?? koment??r alebo z??ska?? koment??re z datab??zy. (" + e.getMessage() + ")");
        }
    }

    private int getPlayingSeconds(long endMillis) {
        return (int) (endMillis - startMillis) / 1000;
    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.println("X ??? ukon??enie hry, MA1 ??? ozna??enie dla??dice v riadku A a st??pci 1, OB4 ??? odkrytie dla??dice v riadku B a st??pci 4");
        String input = readLine().trim().toUpperCase();
        try {
            handleInput(input);
        } catch (WrongFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        if (input.equals("X")) {
            System.exit(0);
        }

        Matcher matcher = PATTERN.matcher(input);

        if (!matcher.matches()) {
            throw new WrongFormatException("Neplatn?? ??ah!");
        }

        int row = matcher.group(2).charAt(0) - LETTER_ASCII;
        int column = Integer.parseInt(matcher.group(3));

        if (row < 0 || row >= field.getRowCount() || column < 0 || column >= field.getColumnCount())
            throw new WrongFormatException("Zadal si ??ah mimo po??a!");

        if (matcher.group(1).equals("M")) {
            field.markTile(row, column);
        } else {
            field.openTile(row, column);
        }
    }
}
