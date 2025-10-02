package com.mirea.nabiulingb.gamecatalog.domain.repositories;

import com.mirea.nabiulingb.gamecatalog.domain.models.WishlistItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public interface WishlistRepository {
    List<WishlistItem> getWishlist(int userId);
    boolean addToWishlist(int userId, int gameId);
    boolean removeFromWishlist(int userId, int gameId);
    Map<Integer, Integer> checkDiscounts(int userId);
}