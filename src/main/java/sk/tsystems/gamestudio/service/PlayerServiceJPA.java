package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class PlayerServiceJPA implements PlayerService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Player getPlayerByUserName(String uName) {
        if (entityManager
                .createQuery("select p from Player p where p.userName = :username")
                .setParameter("username", uName)
                .getResultList()
                .isEmpty())
            return null;
        return (Player) entityManager
                .createQuery("select p from Player p where p.userName = :username")
                .setParameter("username", uName)
                .getSingleResult();
    }

    @Override
    public void addPlayer(Player player) {
        entityManager.persist(player);
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }
}
