package com.project.myambulance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.myambulance.adapter.HistoryAdapter;
import com.project.myambulance.databinding.ActivityHistoryBinding;
import com.project.myambulance.model.History;
import com.project.myambulance.model.ResponseList;
import com.project.myambulance.remote.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private HistoryAdapter historyAdapter;
    private ActivityHistoryBinding activityHistoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        activityHistoryBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        View view = activityHistoryBinding.getRoot();
        setContentView(view);
        onInit();
        back();

    }

    private void back() {
        activityHistoryBinding.fbBack.setOnClickListener(view -> {
            Intent back = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(back);

        });
    }

    private void onInit() {
        historyAdapter = new HistoryAdapter();
        activityHistoryBinding.vw.setHasFixedSize(true);
        activityHistoryBinding.vw.setLayoutManager(new LinearLayoutManager(this));
        activityHistoryBinding.vw.setAdapter(historyAdapter);
        getData();
    }

    private void getData() {
        Network.provideApiService().getHistory().enqueue(new Callback<ResponseList<History>>() {
            @Override
            public void onResponse(Call<ResponseList<History>> call, Response<ResponseList<History>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            if (response.body().getData() != null) {
                                List<History> data = response.body().getData();
                                historyAdapter.setData(data);

                            } else {
                                Toast.makeText(HistoryActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.i("error", "gagal");
                            }
                        } else {
                            Log.i("error", "gagal");
                        }
                    } else {
                        Log.i("error", "gagal");
                    }
                } else {
                    Log.i("error", "gagal");
                }
            }

            @Override
            public void onFailure(Call<ResponseList<History>> call, Throwable t) {
                Log.e("error", t.getLocalizedMessage());
            }
        });
    }


}