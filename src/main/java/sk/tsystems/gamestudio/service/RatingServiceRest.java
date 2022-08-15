package sk.tsystems.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tsystems.gamestudio.entity.Rating;

public class RatingServiceRest implements RatingService {

    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url + "/rating/" + rating.getGame() + "/" + rating.getUsername(), rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        Integer rating = restTemplate.getForEntity(url + "/rating/" + game, Integer.class).getBody();
        return rating;
//        return restTemplate.getForEntity(url + "/rating/" + game, Rating.class).getBody().getRating();
    }

    @Override
    public int getRating(String game, String username) {
        Rating rating = restTemplate.getForEntity(url + "/rating/" + game + "/" + username, Rating.class).getBody();
        System.out.println(rating);
        return rating.getRating();
//        return restTemplate.getForEntity(url + "/rating/" + game, Rating.class).getBody().getRating();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web.");
    }
}
