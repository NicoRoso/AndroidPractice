package com.mirea.nabiulingb.domain.domain.repositories;

import com.mirea.nabiulingb.domain.domain.models.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getGameComments(int gameId);
    boolean addComment(int gameId, int userId, String text, int rating);
}
