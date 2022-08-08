package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    List<Comment> getComments(String game);

    void reset();
}
