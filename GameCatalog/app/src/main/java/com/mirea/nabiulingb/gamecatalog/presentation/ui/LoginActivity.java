package com.mirea.nabiulingb.gamecatalog.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mirea.nabiulingb.gamecatalog.R;
import com.mirea.nabiulingb.data.repositories.AuthRepositoryImpl;
import com.mirea.nabiulingb.domain.usecases.auth.LoginUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private AuthRepositoryImpl authRepository;
    private LoginUser loginUserUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authRepository = new AuthRepositoryImpl(this);
        if (authRepository.isUserLoggedIn()) {
            startMainActivity();
            return;
        }

        initRepositories();
        initUI();
    }

    private void initRepositories() {
        authRepository = new AuthRepositoryImpl(this);
        loginUserUseCase = new LoginUser(authRepository);
    }

    private void initUI() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvGoToRegister = findViewById(R.id.tvGoToRegister);

        btnLogin.setOnClickListener(v -> attemptLogin());
        tvGoToRegister.setOnClickListener(v -> goToRegister());
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            showToast("Заполните все поля");
            return;
        }

        if (!isValidEmail(email)) {
            showToast("Некорректный формат email");
            return;
        }

        showToast("Вход...");

        new Thread(() -> {
            var user = loginUserUseCase.execute(email, password);
            runOnUiThread(() -> {
                if (user != null) {
                    showToast("Авторизация успешна!");
                    startMainActivity();
                } else {
                    showToast("Ошибка авторизации. Проверьте email и пароль.");
                }
            });
        }).start();
    }

    private void goToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}