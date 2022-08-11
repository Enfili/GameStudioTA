package sk.tsystems.gamestudio.minesweeper;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class PlaygroundJPA {

    @PersistenceContext
    private EntityManager entityManager;

    public void play() {
//        entityManager.persist(new Score("minesweeper", "stefan2", 10, new Date()));
//        entityManager.persist(new Rating("minesweeper", "stefan2", 3, new Date()));
//        entityManager.persist(new Rating("minesweeper", "stefan2", 5, new Date()));
//        entityManager.persist(new Rating("minesweeper", "stefan5", 1, new Date()));
//        entityManager.persist(new Comment("minesweeper", "stefan2", "hihi", new Date()));
//        entityManager.persist(new Rating("minesweeper", "stefan", 4, new Date()));

        String game = "minesweeper";
        String username = "stefan";

//        Rating rating = new Rating("minesweeper", "stefan2", 5, new Date());

        Rating ratingToWrite = null;

        try {
            ratingToWrite = (Rating) entityManager
                    .createQuery("select r from Rating r where r.username = :user and r.game = :game")
                    .setParameter("user", username)
                    .setParameter("game", game)
                    .getSingleResult();
            ratingToWrite.setRating(5);
            ratingToWrite.setRatedOn(new Date());
        } catch (NoResultException e) {
            entityManager.persist(new Rating(game, "stefan", 3, new Date()));
        }


//        if (entityManager
//                .createQuery("select r from Rating r where r.game = :myGame and username = :myUsername")
//                .setParameter("myGame", game)
//                .setParameter("myUsername", username)
//                .getResultList()
//                .size() == 0) {
//            entityManager.persist(rating);
//        } else {
//            entityManager
//                    .createNativeQuery("update rating set rating = ?, rated_on = ? where game = ? and username = ?")
//                    .setParameter(3, game)
//                    .setParameter(4, username)
//                    .setParameter(1, rating.getRating())
//                    .setParameter(2, rating.getRatedOn());
//        }

//        List<Score> bestScores =
//                entityManager
//                .createQuery("select s from Score s where s.game = :myGame order by s.points desc")
//                .setParameter( "myGame", game)
//                .getResultList();
//
//        Number number =
//                (Number) (entityManager
//                        .createQuery("select avg(r.rating) from Rating r where r.game = :myGame")
//                        .setParameter("myGame", game)
//                        .getSingleResult());
//        double avgRating = (double) number;
//
//        int rating =
//                entityManager
//                        .createQuery("select r.rating from Rating r where r.game = :myGame and r.username = :myUsername")
//                        .setParameter("myGame", game)
//                        .setParameter("myUsername", username)
//                        .getFirstResult();
//
//        List<Comment> comments =
//                entityManager
//                .createQuery("select c from Comment c where c.game = :myGame")
//                .setParameter( "myGame", game)
//                .getResultList();
//
//        System.out.println("BEST SCORES: " + bestScores);
//        System.out.println("AVERAGE RATING: " + avgRating);
//        System.out.println("RATING: " + rating);
//        System.out.println("COMMENTS: " + comments);
    }
}
