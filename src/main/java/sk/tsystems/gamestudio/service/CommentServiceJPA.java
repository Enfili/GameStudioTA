package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return entityManager
                .createQuery("select c from Comment c where c.game = :myGame")
                .setParameter( "myGame", game)
                .getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating").executeUpdate();
    }
}
