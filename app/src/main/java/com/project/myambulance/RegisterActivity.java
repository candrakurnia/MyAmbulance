package com.project.myambulance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.myambulance.databinding.ActivityRegisterBinding;
import com.project.myambulance.model.ResponseList;
import com.project.myambulance.model.User;
import com.project.myambulance.remote.Network;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding activityRegisterBinding;
    private String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = activityRegisterBinding.getRoot();
        setContentView(view);
        onInit();
    }

    private void onInit() {
        activityRegisterBinding.btnRegister.setOnClickListener(view -> validation());
    }

    private void validation() {
        String username = Objects.requireNonNull(activityRegisterBinding.edtUsername.getText()).toString();
        String password = Objects.requireNonNull(activityRegisterBinding.edtPassword.getText()).toString();
        String no_telpon = Objects.requireNonNull(activityRegisterBinding.edtPhone.getText()).toString();
        String no_ktp = Objects.requireNonNull(activityRegisterBinding.edtIdcard.getText()).toString();
        String no_kk = Objects.requireNonNull(activityRegisterBinding.edtKk.getText()).toString();

        if (username.length() < 1) {
            Toast.makeText(RegisterActivity.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 1) {
            Toast.makeText(RegisterActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password terlalu pendek", Toast.LENGTH_SHORT).show();
        } else if (no_telpon.length() < 1) {
            Toast.makeText(RegisterActivity.this, "Nomor telpon tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (no_ktp.length() < 1) {
            Toast.makeText(RegisterActivity.this, "Nomor KTP tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (no_kk.length() < 1) {
            Toast.makeText(RegisterActivity.this, "Nomor KK tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            postRegister(username,password,no_telpon,no_kk,no_ktp);
        }
    }

    private void postRegister(String username, String password, String no_telpon, String no_kk, String no_ktp) {
        Network.provideApiService().register(username, password, no_telpon, no_kk, no_ktp).enqueue(new Callback<ResponseList<User>>() {
            @Override
            public void onResponse(Call<ResponseList<User>> call, Response<ResponseList<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            List<User> user = response.body().getData();
                            initLogin(user);
                        } else {
                            Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "GAGAL REGISTER", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "GAGAL REGISTER 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseList<User>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }

    private void initLogin(List<User> user) {
        Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}