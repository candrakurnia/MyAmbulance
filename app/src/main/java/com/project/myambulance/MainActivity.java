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
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.project.myambulance.adapter.HistoryAdapter;
import com.project.myambulance.databinding.ActivityMainBinding;
import com.project.myambulance.helpers.UiHelper;
import com.project.myambulance.model.History;
import com.project.myambulance.model.Lokasi;
import com.project.myambulance.model.ResponseList;
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
    private AlertDialog.Builder builder;
    private boolean locationflag;
    private String alamatText;
    private UiHelper uiHelper;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient locationProviderClient;
    private RelativeLayout lynocon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        ConstraintLayout view = activityMainBinding.getRoot();
        setContentView(view);
        onInit();
        checkHistory();
        onLogout();
    }

    private void onLogout() {
        activityMainBinding.fbHistory.setOnClickListener(view -> {
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.logout(this);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void checkHistory() {
        historyAdapter = new HistoryAdapter();
        activityMainBinding.vw.setAdapter(historyAdapter);
        getData();
    }

    private void getData() {
        Network.provideApiService().getHistory(Objects.requireNonNull(SessionManager.getUser(this)).getNo_ktp()).enqueue(new Callback<ResponseList<History>>() {
            @Override
            public void onResponse(Call<ResponseList<History>> call, Response<ResponseList<History>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            if (response.body().getData() != null) {
                                activityMainBinding.tvNoData.setVisibility(View.GONE);
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


    private void onInit() {
        activityMainBinding.tvNoData.setVisibility(View.VISIBLE);
        activityMainBinding.tvUsername.setText(Objects.requireNonNull(SessionManager.getUser(this)).getUsername());
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        uiHelper = new UiHelper(this);
        locationRequest = uiHelper.getLocationRequest();
        if (!uiHelper.isPlayServicesAvailable()) {
            Toast.makeText(this, "Play Services did not installed!", Toast.LENGTH_SHORT).show();
            finish();
        } else requestLocationUpdates();
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
                            address += listAddresses.get(0).getThoroughfare() + ",";
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


            /*private void onClick() {
                Network.provideApiService().order(SessionManager.getUser(MainActivity.this).getNo_ktp(),alamatText)
                        .enqueue(new Callback<ResponseList<Lokasi>>() {
                            @Override
                            public void onResponse(Call<ResponseList<Lokasi>> call, Response<ResponseList<Lokasi>> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getStatus()) {
                                            List<Lokasi> lokasi = response.body().getData();
                                            if (lokasi != null) {
                                                startActivity(new Intent(MainActivity.this, DriverActivity.class));
                                                Toast.makeText(MainActivity.this, "Berhasil yeay", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(MainActivity.this, "lokasi kosong", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(MainActivity.this, "status null", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "data kosong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseList<Lokasi>> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                            }
                        });
            }*/

            private void onClick() {
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah Anda yakin ingin memesan Ambulance?");
                builder.setPositiveButton("Ya", (dialogInterface, i) -> Network.provideApiService().order((Objects.requireNonNull(SessionManager.getUser(MainActivity.this))).getNo_ktp(), alamatText)
                        .enqueue(new Callback<ResponseList<Lokasi>>() {
                    @Override
                    public void onResponse(Call<ResponseList<Lokasi>> call, Response<ResponseList<Lokasi>> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus()) {
                                    List<Lokasi> lokasi = response.body().getData();
                                        if (lokasi != null) {
                                            startActivity(new Intent(MainActivity.this, DriverActivity.class));
                                            Toast.makeText(MainActivity.this, "Berhasil yeay", Toast.LENGTH_SHORT).show();
                                            finish();
                                    } else {
                                            Toast.makeText(MainActivity.this, "alamat kosong", Toast.LENGTH_SHORT).show();
                                        }

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
                    public void onFailure(Call<ResponseList<Lokasi>> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                }));
                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                });
                builder.show();

            }
        };


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




   /* private void getCovid() {
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
    }*/

}