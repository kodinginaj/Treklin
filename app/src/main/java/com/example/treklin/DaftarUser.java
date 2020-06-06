package com.example.treklin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarUser extends Activity {

    private EditText etNama, etEmail, etPassword, etKPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_user);

        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etKPassword = findViewById(R.id.etKPassword);
        btnRegister = findViewById(R.id.btnRegister);

        TextView textView = findViewById(R.id.textlogin);

        String text = "Jika sudah mempunyai akun silakan Login";

        SpannableString daftar = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent i = new Intent(DaftarUser.this, LoginUser.class);
                startActivity(i);
            }
        };
        daftar.setSpan(clickableSpan1, 34, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(daftar);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = etNama.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String kpassword = etKPassword.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (nama.isEmpty()) {
                    etNama.setError("Nama  harus diisi!");
                }
                if (email.isEmpty()) {
                    etEmail.setError("Email harus diisi!");
                } else if (!email.matches(emailPattern)) {
                    etEmail.setError("Email tidak sesuai format!");
                }
                if (password.isEmpty()) {
                    etPassword.setError("Password harus diisi!");
                } else if (password.length() < 6) {
                    etPassword.setError("Password minimal 6 karakter!");
                } else if (!password.equals(kpassword)) {
                    etPassword.setError("Password tidak sama!");
                }
                if (kpassword.isEmpty()) {
                    etKPassword.setError("Konfirmasi password harus diisi!");
                } else if (password.length() < 6) {
                    etKPassword.setError("Konfirmasi password minimal 6 karakter!");
                } else if (!kpassword.equals(password)) {
                    etKPassword.setError("Konfirmasi password tidak sama!");
                }
                if (!nama.isEmpty() && !email.isEmpty() && email.matches(emailPattern) && !password.isEmpty() && password.length() >= 6 && password.equals(kpassword) && !kpassword.isEmpty() && kpassword.length() >= 6 && kpassword.equals(password)) {

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> registerUser = api.registerUser(nama, email, password);
                    registerUser.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(DaftarUser.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent pindah = new Intent(DaftarUser.this, LoginUser.class);
                            startActivity(pindah);
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(DaftarUser.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}