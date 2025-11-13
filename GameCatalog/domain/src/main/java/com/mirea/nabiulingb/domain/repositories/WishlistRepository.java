package com.mirea.nabiulingb.domain.domain.repositories;

import com.mirea.nabiulingb.domain.domain.models.WishlistItem;

import java.util.List;
import java.util.Map;

public interface WishlistRepository {
    List<WishlistItem> getWishlist(int userId);
    boolean addToWishlist(int userId, int gameId);
    boolean removeFromWishlist(int userId, int gameId);
    Map<Integer, Integer> checkDiscounts(int userId);
}