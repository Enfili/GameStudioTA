package sk.tsystems.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Country;
import sk.tsystems.gamestudio.minesweeper.PlaygroundJPA;
import sk.tsystems.gamestudio.minesweeper.consoleui.ConsoleUI;
import sk.tsystems.gamestudio.server.GameStudioServer;
import sk.tsystems.gamestudio.service.*;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tsystems.gamestudio.server.*"))
public class SpringClient {

    public static void main(String[] args) {
//        SpringApplication.run(SpringClient.class);
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    public CommandLineRunner runner(sk.tsystems.gamestudio.kamene.userInterface.ConsoleUI console) {
        return s -> console.play();
    }

//    @Bean
    public CommandLineRunner runner(ConsoleUI console) {
        return s -> console.play();
    }

//    @Bean
    public CommandLineRunner runnerJPA(PlaygroundJPA console) {
        return s -> console.play();
    }

    @Bean
    public PlaygroundJPA consoleJPA() {
        return new PlaygroundJPA();
    }

    @Bean
    public sk.tsystems.gamestudio.kamene.userInterface.ConsoleUI kameneConsole() {
        return new sk.tsystems.gamestudio.kamene.userInterface.ConsoleUI();
    }

    @Bean
    public ConsoleUI console() {
        return new ConsoleUI();
    }

    @Bean
    public PlayerService playerService() {
        return new PlayerServiceJPA();
    }

    @Bean
    public CountryService countryService() {
        return new CountryServiceJPA();
    }

    @Bean
    public OccupationService occupationService() {
        return new OccupationServiceJPA();
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRest();
//        return new ScoreServiceJPA();
        //return new ScoreServiceJDBC();
    }

    @Bean
    public CommentService commentService() {
//        return new CommentServiceJPA();
        return new CommentServiceRest();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
//        return new RatingServiceRest();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}


/*
import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }
}
*/


/*
import org.springframework.boot.SpringApplication;
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }
}
*/