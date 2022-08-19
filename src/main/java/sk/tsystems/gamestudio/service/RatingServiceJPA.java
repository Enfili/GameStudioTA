package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        Rating ratingToWrite = null;

        try {
            ratingToWrite = (Rating) entityManager
                    .createQuery("select r from Rating r where r.username = :user and r.game = :game")
                    .setParameter("user", rating.getUsername())
                    .setParameter("game", rating.getGame())
                    .getSingleResult();
            ratingToWrite.setRating(rating.getRating());
            ratingToWrite.setRatedOn(rating.getRatedOn());
        } catch (NoResultException e) {
            entityManager.persist(rating);
        }
    }

    @Override
    public int getAverageRating(String game) {
        return ((Number) (entityManager
                .createQuery("select round(avg(r.rating)) from Rating r where r.game = :myGame")
                .setParameter("myGame", game)
                .getSingleResult()))
                .intValue();
    }

    @Override
    public int getRating(String game, String username) {
        return entityManager
                .createQuery("select r.rating from Rating r where r.game = :myGame and r.username = :myUsername")
                .setParameter("myGame", game)
                .setParameter("myUsername", username)
                .getFirstResult();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating").executeUpdate();
    }
}
