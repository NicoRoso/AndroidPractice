package com.mirea.nabiulingb.gamecatalog.domain.repositories;

import com.mirea.nabiulingb.gamecatalog.domain.models.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getGameComments(int gameId);
    boolean addComment(int gameId, int userId, String text, int rating);
}
