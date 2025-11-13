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
import com.mirea.nabiulingb.domain.usecases.auth.RegisterUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private AuthRepositoryImpl authRepository;
    private RegisterUser registerUserUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initRepositories();
        initUI();
    }

    private void initRepositories() {
        authRepository = new AuthRepositoryImpl(this);
        registerUserUseCase = new RegisterUser(authRepository);
    }

    private void initUI() {
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvGoToLogin = findViewById(R.id.tvGoToLogin);

        btnRegister.setOnClickListener(v -> attemptRegister());
        tvGoToLogin.setOnClickListener(v -> goToLogin());
    }

    private void attemptRegister() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Заполните все поля");
            return;
        }

        if (!isValidEmail(email)) {
            showToast("Некорректный формат email");
            return;
        }

        if (password.length() < 6) {
            showToast("Пароль должен содержать минимум 6 символов");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("Пароли не совпадают");
            return;
        }

        if (username.length() < 3) {
            showToast("Имя пользователя должно содержать минимум 3 символа");
            return;
        }

        showToast("Регистрация...");

        new Thread(() -> {
            try {
                var user = registerUserUseCase.execute(username, email, password);
                runOnUiThread(() -> {
                    if (user != null) {
                        showToast("Регистрация успешна!");
                        startMainActivity();
                    } else {
                        showToast("Ошибка регистрации. Возможно email уже используется.");
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> showToast("Ошибка: " + e.getMessage()));
            }
        }).start();
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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