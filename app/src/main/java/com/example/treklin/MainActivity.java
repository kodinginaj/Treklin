package com.example.treklin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treklin.adapter.AdapterArticle;
import com.example.treklin.api.ApiRequest;
import com.example.treklin.api.Retroserver;
import com.example.treklin.model.ArticleModel;
import com.example.treklin.model.ResponseModelArticle;
import com.example.treklin.util.Session;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<ArticleModel> mItems = new ArrayList<>();
    private GoogleMap mMap;
    private ScrollView scrollView;
    private Double latitude, longitude;
    private TextView tvKoordinat;
    private static final int MY_MAPS_REQUEST_CODE = 100;
    Session session;

    Geocoder geocoder;
    List<Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        tvKoordinat = findViewById(R.id.tvKoordinat);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        session = new Session(MainActivity.this);


        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }else{
            mFusedLocation.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        String alamat = getAddress(latitude, longitude);
                        session.setAlamat(alamat);

                        tvKoordinat.setText(alamat);
//                    refresh(1000);

                        LatLng posisi = new LatLng(latitude, longitude);
                        float zoomLevel = 16.0f;

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, zoomLevel));
                    }
                }
            });
        }


        navigationView.getMenu().findItem(R.id.artikel).setOnMenuItemClickListener(menuItem -> {
            View targetView = findViewById(R.id.txtarticle);
            scrollView.smoothScrollTo(10, (int) targetView.getBottom());
            return true;
        });

        navigationView.getMenu().findItem(R.id.tracking).setOnMenuItemClickListener(menuItem -> {
            Intent pindah = new Intent(MainActivity.this, TrackingUser.class);
            pindah.putExtra("halaman", "tracking");
            startActivity(pindah);
            return true;
        });

        navigationView.getMenu().findItem(R.id.home).setOnMenuItemClickListener(menuItem -> {
            Intent pindah = new Intent(MainActivity.this, MainActivity.class);
            startActivity(pindah);
            finish();
            return true;
        });

        navigationView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
            confirmLogout();
            return true;
        });

        scrollView = findViewById(R.id.scrollViewMain);
        recyclerView = findViewById(R.id.rvArticle);
        mManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mManager);

        ApiRequest apiRequest = Retroserver.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelArticle> getArticle = apiRequest.getArticle();
        getArticle.enqueue(new Callback<ResponseModelArticle>() {
            @Override
            public void onResponse(Call<ResponseModelArticle> call, Response<ResponseModelArticle> response) {
                mItems = response.body().getData();
                if (mItems.size() > 0) {
                    mAdapter = new AdapterArticle(MainActivity.this, mItems);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelArticle> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ada kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void confirmLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Apakah anda yakin untuk logout?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                session.logout();
                Intent pindah = new Intent(MainActivity.this, LoginUser.class);
                startActivity(pindah);
                finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_MAPS_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent pindah = new Intent(MainActivity.this, TrackingUser.class);
                pindah.putExtra("halaman", "tracking");
                startActivity(pindah);
            }
        });
    }

//    private void refresh(int milisecond) {
//        final Handler handler = new Handler();
//
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Log.d("HEHE", "Tes");
//
//                FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(MainActivity.this);
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    mFusedLocation.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location != null) {
//
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//
//                                tvKoordinat.setText("Latitude =" + latitude + " Longitude =" + longitude);
//
//                                LatLng posisi = new LatLng(latitude, longitude);
//                                float zoomLevel = 16.0f;
//
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, zoomLevel));
//                            }
//                        }
//                    });
//                }
//
//                refresh(5000);
//            }
//        };
//        handler.postDelayed(runnable,milisecond);
//    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getThoroughfare());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }
}
