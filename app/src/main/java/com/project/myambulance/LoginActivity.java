package com.project.myambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reg = findViewById(R.id.tv_regist);
        login = findViewById(R.id.btn_login);

        reg.setOnClickListener(view -> {
            Intent regist = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(regist);
            finish();
        });
        login.setOnClickListener(view -> {
            Intent login = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(login);
            finish();
        });

    }
}