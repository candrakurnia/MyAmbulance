package com.project.myambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.project.myambulance.databinding.ActivitySplashScreenBinding;
import com.project.myambulance.pref.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        View view = activitySplashScreenBinding.getRoot();
        setContentView(view);
        initSplash();

    }

    private void initSplash() {
        new Handler().postDelayed((Runnable) () -> {
            Boolean isLogin = SessionManager.getIsLogin(this);
            if (isLogin) {
                initMain();
            } else {
                initBoarding();
            }
        }, 2000);
    }

    private void initBoarding() {
        Intent boarding = new Intent(SplashScreenActivity.this, OnboardingActivity.class);
        startActivity(boarding);
        finish();
    }

    private void initMain() {
        Intent main = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(main);
        finish();
    }
}