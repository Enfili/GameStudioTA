package sk.tsystems.gamestudio.minesweeper;

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

        entityManager.persist(new Score("minesweeper", "stefan", 10, new Date()));

        String game = "minesweeper";

        List<Score> bestScores =
                entityManager
                .createQuery("select s from Score s where s.game = :myGame order by s.points desc")
                .setParameter( "myGame", game)
                .getResultList();

        System.out.println(bestScores);

        System.out.println("Closing JPA playground.");
    }
}
