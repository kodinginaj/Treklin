package com.example.treklin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treklin.api.ApiRequest;
import com.example.treklin.api.Retroserver;
import com.example.treklin.model.ResponseModel;
import com.example.treklin.model.UserModel;
import com.example.treklin.util.Session;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUser extends Activity {

    private EditText etEmail, etPassword;
    private Double latitude, longitude;
    private Button btnLogin;
    private static final int MY_MAPS_REQUEST_CODE = 100;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_MAPS_REQUEST_CODE);
        }

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(LoginUser.this);
        mFusedLocation.getLastLocation().addOnSuccessListener(LoginUser.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        session = new Session(LoginUser.this);

        TextView textView = findViewById(R.id.textregist);
        //test

        String text = "Jika belum mempunyai akun silakan Daftar";

        SpannableString daftar = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(LoginUser.this, DaftarUser.class);
                startActivity(i);
            }
        };
        daftar.setSpan(clickableSpan1, 34, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(daftar);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty()) {
                    etEmail.setError("Email harus diisi!");
                }
                if (password.isEmpty()) {
                    etPassword.setError("Password harus diisi!");
                }
                if (!email.isEmpty() && !password.isEmpty()) {

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> login = api.login(email, password, latitude, longitude);
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(LoginUser.this);
                    progressDialog.setMessage("Mohon tunggu....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    login.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.body().getStatus().equals("1")) {
                                UserModel userModel = response.body().getDataUser();
                                session.setId(userModel.getId());
                                session.setNama(userModel.getNama());
                                session.setEmail(userModel.getEmail());
                                session.setLatitude(userModel.getLatitude());
                                session.setLongitude(userModel.getLongitude());
                                Intent pindah = new Intent(LoginUser.this, MainActivity.class);
                                startActivity(pindah);
                                progressDialog.dismiss();
                            } else if (response.body().getStatus().equals("0")) {
                                Toast.makeText(LoginUser.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(LoginUser.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_MAPS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin akses lokasi diberikan.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Izin akses lokasi ditolak.", Toast.LENGTH_LONG).show();
            }
        }
    }

}