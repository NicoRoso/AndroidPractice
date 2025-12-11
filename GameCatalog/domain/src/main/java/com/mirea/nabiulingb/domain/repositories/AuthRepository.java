package com.mirea.nabiulingb.domain.repositories;

import com.mirea.nabiulingb.domain.models.User;

public interface AuthRepository {
    User login(String email, String password);
    User loginWithOAuth(String provider, String token);
    User register(String username, String email, String password);

    String getUserName();
    boolean isUserLoggedIn();
    void logout();
    String getUserEmail();
}