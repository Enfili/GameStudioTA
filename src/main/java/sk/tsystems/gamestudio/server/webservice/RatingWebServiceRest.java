package sk.tsystems.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingWebServiceRest {

    @Autowired
    RatingService ratingService;

    @GetMapping("/{game}/{username}")
    public int getRating(@PathVariable String game, @PathVariable String username) {
        return ratingService.getRating(game, "Tajomstvo");
    }

    @GetMapping("/{game}")
    public int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    @PostMapping
    public void addRating(@RequestBody Rating rating) {ratingService.setRating(rating);}
}
