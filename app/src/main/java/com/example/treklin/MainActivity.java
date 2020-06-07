package com.example.treklin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.treklin.adapter.AdapterArticle;
import com.example.treklin.api.ApiRequest;
import com.example.treklin.api.Retroserver;
import com.example.treklin.model.ArticleModel;
import com.example.treklin.model.ResponseModel;
import com.example.treklin.model.ResponseModelArticle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<ArticleModel> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);

        navigationView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
            confirmLogout();
            return true;
        });

        recyclerView = findViewById(R.id.rvArticle);
        mManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mManager);

        ApiRequest apiRequest = Retroserver.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelArticle> getArticle = apiRequest.getArticle();
        getArticle.enqueue(new Callback<ResponseModelArticle>() {
            @Override
            public void onResponse(Call<ResponseModelArticle> call, Response<ResponseModelArticle> response) {
                mItems = response.body().getData();
                if (mItems.size() > 0){
                    mAdapter = new AdapterArticle(MainActivity.this, mItems);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModelArticle> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ada kesalahan koneksi",Toast.LENGTH_SHORT).show();
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
                Intent pindah = new Intent(MainActivity.this, LoginUser.class);
                startActivity(pindah);
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
}
