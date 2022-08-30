package sk.tsystems.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import sk.tsystems.gamestudio.service.*;


@SpringBootApplication
@EntityScan(basePackages = "sk.tsystems.gamestudio.entity")
public class GameStudioServer {

    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class);
    }

    @Bean
    public ScoreService scoreService() { return new ScoreServiceJPA(); }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }

    @Bean
    public CountryService countryService() { return new CountryServiceJPA();}

    @Bean
    public OccupationService occupationService() {return new OccupationServiceJPA();}

    @Bean
    public PlayerService playerService() {
        return new PlayerServiceJPA();
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

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