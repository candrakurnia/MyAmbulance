package com.project.myambulance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.project.myambulance.adapter.HistoryAdapter;
import com.project.myambulance.databinding.ActivityMainBinding;
import com.project.myambulance.databinding.FragmentHomeBinding;
import com.project.myambulance.fragment.HistoryFragment;
import com.project.myambulance.fragment.HomeFragment;
import com.project.myambulance.fragment.SettingsFragment;
import com.project.myambulance.model.DataCovid;
import com.project.myambulance.model.History;
import com.project.myambulance.model.ResponseList;
import com.project.myambulance.remote.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private HistoryAdapter historyAdapter;
    private final String TAG = "MainActivity";
    private DataCovid dataCovid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        activityMainBinding.btmMenu.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    getCovid();
                    break;
                case R.id.history:
                    replaceFragment(new HistoryFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
//        dataCovid = new DataCovid(dataCovid.getPositif(), dataCovid.getDirawat(), dataCovid.getSembuh(), dataCovid.getMeninggal(), dataCovid.getLastUpdate());
        onInit();

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void onInit() {
        historyAdapter = new HistoryAdapter();
        /*activityMainBinding.vw.setAdapter(historyAdapter);
        activityMainBinding.vw.setClipToPadding(true);*/
        getData();
//        getCovid();

    }
    private void getCovid() {
        Network.provideApiService().getCovid().enqueue(new Callback<DataCovid>() {
            @Override
            public void onResponse(Call<DataCovid> call, Response<DataCovid> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        setData(response.body());
                        Log.d(TAG, "masuk");

                    } else {
                        Log.e(TAG, "Gamasuk1");
                    }
                } else {
                    Log.e(TAG, "Gamasuk 2");
                }
            }

            @Override
            public void onFailure(Call<DataCovid> call, Throwable t) {
                Log.i(TAG, t.getLocalizedMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setData(DataCovid dataCovid) {
//        activityMainBinding.tvDirawat.setText(dataCovid.getDirawat().toString() + " Jiwa");
//        activityMainBinding.tvPositif.setText( dataCovid.getPositif().toString() + " Jiwa");
//        activityMainBinding.tvSembuh.setText(dataCovid.getSembuh().toString() + " Jiwa");
//        activityMainBinding.tvMeninggal.setText(dataCovid.getMeninggal().toString() + " Jiwa");
//        activityMainBinding.tvUpdate.setText(dataCovid.getLastUpdate());
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
                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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