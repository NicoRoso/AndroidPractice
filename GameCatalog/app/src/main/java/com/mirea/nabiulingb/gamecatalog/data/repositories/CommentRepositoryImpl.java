package com.mirea.nabiulingb.gamecatalog.data.repositories;

import com.mirea.nabiulingb.gamecatalog.domain.models.Comment;
import com.mirea.nabiulingb.gamecatalog.domain.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepository {

    private final List<Comment> testComments = createTestComments();

    @Override
    public List<Comment> getGameComments(int gameId) {
        List<Comment> gameComments = new ArrayList<>();
        for (Comment comment : testComments) {
            if (comment.getGameId() == gameId) {
                gameComments.add(comment);
            }
        }
        return gameComments;
    }

    @Override
    public boolean addComment(int gameId, int userId, String text, int rating) {
        return true;
    }

    private List<Comment> createTestComments() {
        List<Comment> comments = new ArrayList<>();

        comments.add(new Comment(1, 1, 1, "TestUser",
                "Amazing game! Great story and gameplay.", 5, "2024-01-15"));

        comments.add(new Comment(2, 1, 2, "Gamer123",
                "One of the best RPGs ever made.", 5, "2024-01-10"));

        comments.add(new Comment(3, 2, 3, "CyberFan",
                "Great visuals but some bugs.", 4, "2024-01-12"));

        comments.add(new Comment(4, 3, 1, "TestUser",
                "Perfect western game with deep story.", 5, "2024-01-08"));

        return comments;
    }
}