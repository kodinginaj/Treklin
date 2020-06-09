package com.example.treklin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.treklin.util.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TrackingUser extends AppCompatActivity {
    private String halaman;
    public String id;
    private AppBarConfiguration mAppBarConfiguration;
    private Session session;
    Locale localeID = new Locale("in", "ID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_user);
        //        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerOpen(GravityCompat.END)){
                    drawer.closeDrawer(GravityCompat.START);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        Intent i = getIntent();
        halaman = i.getStringExtra("halaman");
        id = i.getStringExtra("id");

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(drawer.isDrawerOpen(GravityCompat.END)){
//                    drawer.closeDrawer(GravityCompat.START);
//                }else{
//                    drawer.openDrawer(GravityCompat.END);
//                }
//            }
//        });

        //Ubah 3 Item Drawer Nama
        session = new Session(TrackingUser.this);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy (HH:mm)", localeID);

        String currentDateandTime = sdf.format(new Date());

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setTitle(session.getNama());
        navigationView.getMenu().getItem(0).setCheckable(false);
        navigationView.getMenu().getItem(1).setTitle(currentDateandTime);
        navigationView.getMenu().getItem(1).setCheckable(false);
        navigationView.getMenu().getItem(2).setTitle(session.getAlamat());
        navigationView.getMenu().getItem(2).setCheckable(false);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
////        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);


        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHost.getNavController();
        NavigationUI.setupWithNavController(navigationView, navController);

        NavInflater navInflater = navController.getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.mobile_navigation);
        graph.setStartDestination(R.id.nav_gallery);

        if (halaman.equals("tracking")) {
            graph.setStartDestination(R.id.nav_home);
        } else {
            graph.setStartDestination(R.id.nav_gallery);
        }

        navController.setGraph(graph);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tracking_user, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}