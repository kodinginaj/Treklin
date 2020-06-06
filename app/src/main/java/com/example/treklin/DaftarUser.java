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
import android.widget.TextView;

public class DaftarUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_user);

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
    }
}