package com.mirea.nabiulingb.domain.usecases.games;

import com.mirea.nabiulingb.domain.models.WishlistItem;
import com.mirea.nabiulingb.domain.repositories.WishlistRepository;

import java.util.List;

public class GetWishlist {
    private final WishlistRepository wishlistRepository;

    public GetWishlist(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<WishlistItem> execute(int userId) {
        return wishlistRepository.getWishlist(userId);
    }
}
