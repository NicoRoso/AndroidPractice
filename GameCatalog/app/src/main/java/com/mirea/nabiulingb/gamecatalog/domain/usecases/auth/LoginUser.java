package com.mirea.nabiulingb.gamecatalog.domain.usecases.auth;

import com.mirea.nabiulingb.gamecatalog.domain.models.User;
import com.mirea.nabiulingb.gamecatalog.domain.repositories.AuthRepository;

public class LoginUser {
    private final AuthRepository authRepository;

    public LoginUser(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public User execute(String email, String password) {
        return authRepository.login(email, password);
    }
}
