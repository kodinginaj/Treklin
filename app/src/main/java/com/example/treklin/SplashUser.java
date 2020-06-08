package com.example.treklin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.treklin.util.Session;

public class SplashUser extends Activity {

    Handler handler;
    Runnable runnable;
    ImageView img;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_user);

        img = findViewById(R.id.myimggg);
        img.animate().alpha(4000).setDuration(0);
        session = new Session(SplashUser.this);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String nama = session.getNama();
                if (nama != null){
                    Intent i = new Intent(SplashUser.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashUser.this, LoginUser.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 4000);
    }
}