package com.mirea.nabiulingb.domain.usecases.auth;

import com.mirea.nabiulingb.domain.models.User;
import com.mirea.nabiulingb.domain.repositories.AuthRepository;

public class LoginUser {
    private final AuthRepository authRepository;

    public LoginUser(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public User execute(String email, String password) {
        return authRepository.login(email, password);
    }
}
