package test;

import sk.tsystems.gamestudio.entity.Comment;
import org.junit.jupiter.api.Test;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.CommentServiceJDBC;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest {
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testReset() {
        commentService.addComment(new Comment("sk/tsystems/gamestudio", "janko_hrasko", "koment", new Date()));
        commentService.reset();
        assertEquals(0, commentService.getComments("sk/tsystems/gamestudio").size());
    }

    @Test
    public void testAddScore() {
        commentService.reset();
        Date date = new Date();
        commentService.addComment(new Comment("sk/tsystems/gamestudio", "janko_hrasko", "koment", date));

        var comments = commentService.getComments("sk/tsystems/gamestudio");
        assertEquals(1, commentService.getComments("sk/tsystems/gamestudio").size());
        assertEquals("sk/tsystems/gamestudio", comments.get(0).getGame());
        assertEquals("janko_hrasko", comments.get(0).getUsername());
        assertEquals("koment", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());
    }

    @Test
    public void testGetBestScores() {
        commentService.reset();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 10);
        var date1 = cal.getTime();
        commentService.addComment(new Comment("sk/tsystems/gamestudio", "Peto", "koment", date1));
        cal.set(Calendar.YEAR, 1998);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        var date2 = cal.getTime();
        commentService.addComment(new Comment("sk/tsystems/gamestudio", "Katka", "haha", date2));
        cal.set(Calendar.YEAR, 1988);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        var date3 = cal.getTime();
        commentService.addComment(new Comment("tiles", "Zuzka", "koment 2", date3));
        cal.set(Calendar.YEAR, 1998);
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        var date4 = cal.getTime();
        commentService.addComment(new Comment("sk/tsystems/gamestudio", "Jergus", "hihi", date4));

        var comments = commentService.getComments("sk/tsystems/gamestudio");

        assertEquals(3, comments.size());

        assertEquals("sk/tsystems/gamestudio", comments.get(0).getGame());
        assertEquals("Jergus", comments.get(0).getUsername());
        assertEquals("hihi", comments.get(0).getComment());
        assertEquals(date4, comments.get(0).getCommentedOn());

        assertEquals("sk/tsystems/gamestudio", comments.get(1).getGame());
        assertEquals("Katka", comments.get(1).getUsername());
        assertEquals("haha", comments.get(1).getComment());
        assertEquals(date2, comments.get(1).getCommentedOn());

        assertEquals("sk/tsystems/gamestudio", comments.get(2).getGame());
        assertEquals("Peto", comments.get(2).getUsername());
        assertEquals("koment", comments.get(2).getComment());
        assertEquals(date1, comments.get(2).getCommentedOn());
    }
}
