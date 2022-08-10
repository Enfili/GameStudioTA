package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) {
        return entityManager.createQuery("select avg(r.rating) from rating r where r.game = :myGame").setParameter("myGame", game).getFirstResult();
    }

    @Override
    public int getRating(String game, String username) {
        return entityManager.createQuery("select avg(r.rating) from Rating r where r.game = :myGame and r.username = :myUsername").setParameter("myGame", game).setParameter("myUsername", username).getFirstResult();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating").executeUpdate();
    }
}
