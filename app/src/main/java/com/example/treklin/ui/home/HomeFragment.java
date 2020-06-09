package com.example.treklin.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treklin.MainActivity;
import com.example.treklin.R;
import com.example.treklin.TrackingUser;
import com.example.treklin.adapter.AdapterOfficer;
import com.example.treklin.api.ApiRequest;
import com.example.treklin.api.Retroserver;
import com.example.treklin.model.OfficerModel;
import com.example.treklin.model.ResponseModelOfficer;
import com.example.treklin.util.Session;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;
    private TextView tvKoordinat;
    private GoogleMap mMap;
    private Double latitude, longitude;
    private static final int MY_MAPS_REQUEST_CODE = 100;
    Handler handler;

    private RecyclerView tampilOfficer;
    private RecyclerView.LayoutManager layoutOfficer;
    private RecyclerView.Adapter adapterOfficer;

    private List<OfficerModel> listOfficer;
    private List<OfficerModel> itemOfficer = new ArrayList<>();
    private Session session;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tvKoordinat = root.findViewById(R.id.tvKoordinat);

        tampilOfficer = root.findViewById(R.id.tampilOfficer);
        layoutOfficer = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        tampilOfficer.setLayoutManager(layoutOfficer);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(getContext());
        mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    String alamat = getAddress(latitude,longitude);

                    tvKoordinat.setText(alamat);
                    refresh(1000);

                    LatLng posisi = new LatLng(latitude, longitude);
                    float zoomLevel = 16.0f;

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, zoomLevel));
                    

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModelOfficer> getOfficer = api.getOfficer();
                    getOfficer.enqueue(new Callback<ResponseModelOfficer>() {
                        @Override
                        public void onResponse(Call<ResponseModelOfficer> call, Response<ResponseModelOfficer> response) {
                            listOfficer = response.body().getOfficer();
                            itemOfficer = response.body().getOfficer();

                            for (int i=0;i<listOfficer.size();i++) {
                                OfficerModel officer = listOfficer.get(i);

                                double latitude = Double.parseDouble(officer.getLatitude());
                                double longtitude = Double.parseDouble(officer.getLongitude());
                                LatLng posisi = new LatLng(latitude, longtitude);

//                                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.mapsicon);

                                mMap.addMarker(new MarkerOptions()
                                        .position(posisi)
                                        .title(officer.getNama()));

                                //Set ke getJarak()

                                session = new Session(getContext());
                                Location startPoint=new Location("locationA");
                                startPoint.setLatitude(Double.parseDouble(session.getLatitude()));
                                startPoint.setLongitude(Double.parseDouble(session.getLongitude()));

                                Location endPoint=new Location("locationB");
                                endPoint.setLatitude(latitude);
                                endPoint.setLongitude(longtitude);

                                Float distance = startPoint.distanceTo(endPoint)/1000;

                                itemOfficer.get(i).setJarak(distance);
                            }

                            Collections.sort(itemOfficer, new Comparator<OfficerModel>() {
                                @Override
                                public int compare(OfficerModel mitraModel, OfficerModel t1) {
                                    return mitraModel.getJarak().compareTo(t1.getJarak());
                                }
                            });

                            adapterOfficer = new AdapterOfficer(getContext(), itemOfficer);
                            tampilOfficer.setAdapter(adapterOfficer);
                            adapterOfficer.notifyDataSetChanged();
                        }
                        @Override
                        public void onFailure(Call<ResponseModelOfficer> call, Throwable t) {
                            Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_MAPS_REQUEST_CODE);
        }else {
            mMap.setMyLocationEnabled(true);
        }
    }


    private void refresh(int milisecond){
        handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("HEHE","Tes");

                FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());
                mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            String alamat = getAddress(latitude,longitude);
                            tvKoordinat.setText(alamat);

                            LatLng posisi = new LatLng(latitude, longitude);
                            float zoomLevel = 16.0f;

//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, zoomLevel));
                        }
                    }
                });
                refresh(5000);
            }
        };
        handler.postDelayed(runnable,milisecond);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler.removeCallbacksAndMessages(null);
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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