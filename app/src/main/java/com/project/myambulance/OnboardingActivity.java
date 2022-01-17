package com.project.myambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class OnboardingActivity extends AppCompatActivity {

    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        start = findViewById(R.id.btn_start);
        start.setOnClickListener(view -> {
            Intent st = new Intent(OnboardingActivity.this, LoginActivity.class);
            startActivity(st);
            finish();
        });
    }
}