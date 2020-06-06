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
import android.widget.TextView;

public class LoginUser extends Activity {

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

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


        button = findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginUser.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

}