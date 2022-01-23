package com.project.myambulance;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.project.myambulance.databinding.ActivityDriverBinding;

public class DriverActivity extends AppCompatActivity {

    ActivityDriverBinding activityDriverBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDriverBinding = ActivityDriverBinding.inflate(getLayoutInflater());
        View view = activityDriverBinding.getRoot();
        setContentView(view);
    }
}