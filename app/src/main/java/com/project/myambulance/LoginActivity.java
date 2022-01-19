package com.project.myambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.myambulance.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = activityLoginBinding.getRoot();
        setContentView(view);
        onInit();


        activityLoginBinding.tvRegist.setOnClickListener(view1 -> {
            Intent login = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(login);
            finish();
        });

    }

    private void onInit() {
        activityLoginBinding.btnLogin.setOnClickListener(view -> {
            validation();
        });
    }

    private void validation() {
        String username = Objects.requireNonNull(activityLoginBinding.edtUsername.getText()).toString();
        String password = Objects.requireNonNull(activityLoginBinding.edtPassword.getText()).toString();
    }
}