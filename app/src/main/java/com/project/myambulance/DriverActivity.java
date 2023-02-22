package com.project.myambulance;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.myambulance.databinding.ActivityDriverBinding;
import com.project.myambulance.model.Driver;
import com.project.myambulance.model.Lokasi;
import com.project.myambulance.model.ResponseData;
import com.project.myambulance.model.ResponseList;
import com.project.myambulance.model.User;
import com.project.myambulance.pref.SessionManager;
import com.project.myambulance.remote.Network;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverActivity extends AppCompatActivity {

    ActivityDriverBinding activityDriverBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDriverBinding = ActivityDriverBinding.inflate(getLayoutInflater());
        View view = activityDriverBinding.getRoot();
        setContentView(view);
        onInit();
    }

    private void onInit() {
        reload();
        getData();
    }

    private void getData() {
        Network.provideApiService().driver(Objects.requireNonNull(SessionManager.getUser(this)).getNo_ktp()).enqueue(new Callback<ResponseData<Driver>>() {
            @Override
            public void onResponse(Call<ResponseData<Driver>> call, Response<ResponseData<Driver>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Driver driver = response.body().getData();
                            if (driver != null) {
                                initData(driver);
                            } else {
                                Toast.makeText(DriverActivity.this, "GAGAL 1", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DriverActivity.this, "GAGAL 2", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DriverActivity.this, "GAGAL 3", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DriverActivity.this, "GAGAL NIH", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Driver>> call, Throwable t) {
                Toast.makeText(DriverActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure" + t.getLocalizedMessage());
            }
        });
    }

    private void reload() {
        activityDriverBinding.btnReload.setOnClickListener(view -> {
            getData();
        });
    }

    private void initData(Driver driver) {
    activityDriverBinding.tvDriver.setText(driver.getUsername());
    activityDriverBinding.tvPositif.setText(driver.getAlamat());
    activityDriverBinding.tvSembuh.setText(driver.getNo_plat());
    }



}