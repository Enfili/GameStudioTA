package sk.tsystems.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sk.tsystems.gamestudio.minesweeper.PlaygroundJPA;
import sk.tsystems.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.service.*;

@SpringBootApplication
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }

//    @Bean
//    public CommandLineRunner runner(ConsoleUI console) {
//        return s -> console.play();
//    }

    @Bean
    public CommandLineRunner runnerJPA(PlaygroundJPA playground) { return s -> playground.play();}

    @Bean
    public PlaygroundJPA playground() {
        return new PlaygroundJPA();
    }

    @Bean
    public ConsoleUI console() {
        return new ConsoleUI();
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() { return new CommentServiceJPA(); }

    @Bean
    public RatingService ratingService() { return new RatingServiceJPA(); }

}
