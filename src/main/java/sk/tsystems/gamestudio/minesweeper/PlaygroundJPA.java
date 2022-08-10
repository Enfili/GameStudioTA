package sk.tsystems.gamestudio.minesweeper;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public class PlaygroundJPA {

    @PersistenceContext
    private EntityManager entityManager;

    public void play() {
        System.out.println("Opening JPA playground.");

        entityManager.persist(new Score("minesweeper", "stefan2", 10, new Date()));
        entityManager.persist(new Rating("minesweeper", "stefan2", 4, new Date()));
        entityManager.persist(new Comment("minesweeper", "stefan2", "hihi", new Date()));

        String game = "minesweeper";

//        List<Score> bestScores =
//                entityManager
//                .createQuery("select s from Score s where s.game = :myGame order by s.points desc")
//                .setParameter( "myGame", game)
//                .getResultList();

//        System.out.println(bestScores);


        System.out.println("Closing JPA playground.");
    }
}
