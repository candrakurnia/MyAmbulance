package com.project.myambulance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.project.myambulance.adapter.HistoryAdapter;
import com.project.myambulance.databinding.ActivityMainBinding;
import com.project.myambulance.helpers.UiHelper;
import com.project.myambulance.model.DataCovid;
import com.project.myambulance.model.ResponseData;
import com.project.myambulance.model.User;
import com.project.myambulance.pref.SessionManager;
import com.project.myambulance.remote.Network;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2161;
    private ActivityMainBinding activityMainBinding;
    private HistoryAdapter historyAdapter;
    private final String TAG = "MainActivity";
    private boolean locationflag;
    private String alamatText;
    private UiHelper uiHelper;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient locationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        activityMainBinding.fbHistory.setOnClickListener(view1 -> {
            Intent Hstry = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(Hstry);
            finish();
        });

        onInit();

    }

    private void onInit() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        uiHelper = new UiHelper(this);
        locationRequest = uiHelper.getLocationRequest();
        if (!uiHelper.isPlayServicesAvailable()) {
            Toast.makeText(this, "Play Services did not installed!", Toast.LENGTH_SHORT).show();
            finish();
        } else requestLocationUpdates();
//        activityMainBinding.fbHistory.setOnClickListener(view -> logout());
        getCovid();

//        historyAdapter = new HistoryAdapter();
//        activityMainBinding.vw.setAdapter(historyAdapter);
//        getData();

    }

        final LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location == null) return;
                if (locationflag) {
                    locationflag = false;
                }

                String address = "Tidak dapat menemukan lokasi";
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                try {
                    List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (listAddresses != null && listAddresses.size() > 0) {
                        address = "";
                        if (listAddresses.get(0).getThoroughfare() != null) {
                            address += listAddresses.get(0).getThoroughfare() + "\n";
                        }
                        if (listAddresses.get(0).getLocality() != null) {
                            address += listAddresses.get(0).getLocality() + ", ";
                        }
                        if (listAddresses.get(0).getPostalCode() != null) {
                            address += listAddresses.get(0).getPostalCode() + ", ";
                        }
                        if (listAddresses.get(0).getAdminArea() != null) {
                            address += listAddresses.get(0).getAdminArea() + ", ";
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                activityMainBinding.tvLoc.setText(address);
                alamatText = address;
                activityMainBinding.btnPesan.setOnClickListener(view -> onClick());

            }
        };


    private void onClick() {
        Network.provideApiService().order(Objects.requireNonNull(SessionManager.getUser(this)).getNo_ktp(),alamatText).enqueue(new Callback<ResponseData<User>>() {
            @Override
            public void onResponse(Call<ResponseData<User>> call, Response<ResponseData<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Toast.makeText(MainActivity.this, "Berhasil yeay", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Gagal Maning 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Gagal Maning 2", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<User>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        if (!uiHelper.isHaveLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        if (uiHelper.isLocationProviderEnabled())
            uiHelper.showPositiveDialogWithListener(this, getResources().getString(R.string.enable_gps_setting), getResources().getString(R.string.location_content),
                    () -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)), "Turn On", false);
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            int value = grantResults[0];
            if (value == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
                finish();
            } else if (value == PackageManager.PERMISSION_GRANTED) requestLocationUpdates();
        }
    }


//    private void getData() {
//        Network.provideApiService().getHistory(Objects.requireNonNull(SessionManager.getUser(this)).getNo_ktp()).enqueue(new Callback<ResponseList<History>>() {
//            @Override
//            public void onResponse(Call<ResponseList<History>> call, Response<ResponseList<History>> response) {
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        if (response.body().getStatus()) {
//                            if (response.body().getData() != null) {
//                                List<History> data = response.body().getData();
//                                historyAdapter.setData(data);
//
//                            } else {
//                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                Log.i("error", "gagal");
//                            }
//                        } else {
//                            Log.i("error", "gagal");
//                        }
//                    } else {
//                        Log.i("error", "gagal");
//                    }
//                } else {
//                    Log.i("error", "gagal");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseList<History>> call, Throwable t) {
//                Log.e("error", t.getLocalizedMessage());
//            }
//        });
//
//    }

    private void logout() {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.logout(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
        activityMainBinding.tvDirawat.setText(dataCovid.getDirawat().toString() + " Jiwa");
        activityMainBinding.tvPositif.setText( dataCovid.getPositif().toString() + " Jiwa");
        activityMainBinding.tvSembuh.setText(dataCovid.getSembuh().toString() + " Jiwa");
        activityMainBinding.tvMeninggal.setText(dataCovid.getMeninggal().toString() + " Jiwa");
        activityMainBinding.tvUpdate.setText(dataCovid.getLastUpdate());
    }

}