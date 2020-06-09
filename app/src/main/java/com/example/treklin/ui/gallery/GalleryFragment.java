package com.example.treklin.ui.gallery;

import android.app.ProgressDialog;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.bumptech.glide.Glide;
import com.example.treklin.R;
import com.example.treklin.TrackingUser;
import com.example.treklin.adapter.AdapterArticle;
import com.example.treklin.api.ApiRequest;
import com.example.treklin.api.Retroserver;
import com.example.treklin.model.ArticleModel;
import com.example.treklin.model.ResponseModelArticle;
import com.google.android.gms.common.api.Api;

import org.w3c.dom.Text;

import java.util.List;

public class GalleryFragment extends Fragment {
    private String id ;
    private GalleryViewModel galleryViewModel;
    private TextView tJudul, tPenulis, tIsi;
    private ImageView iFoto;
    private List<ArticleModel> articles;
    private ArticleModel article;
    Retroserver retroserver;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_artikel, container, false);

        tJudul = root.findViewById(R.id.text_judul);
        tPenulis = root.findViewById(R.id.text_penulis);
        tIsi = root.findViewById(R.id.text_isi);
        iFoto = root.findViewById(R.id.img_article);
        retroserver = new Retroserver();

        TrackingUser trackingUser = (TrackingUser)getActivity();
        id = trackingUser.id;

        recyclerView = root.findViewById(R.id.rvArticleLain);
        mManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mManager);

        ApiRequest apiRequest = Retroserver.getClient().create(ApiRequest.class);
        retrofit2.Call<ResponseModelArticle> getDetailArtcile = apiRequest.getDetailArticle(id);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mohon tunggu....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        getDetailArtcile.enqueue(new Callback<ResponseModelArticle>() {
            @Override
            public void onResponse(Call<ResponseModelArticle> call, Response<ResponseModelArticle> response) {
                if (response.body()!= null){
                    articles = response.body().getData();
                    article = response.body().getArticle();
                    tJudul.setText(article.getJudul());
                    tPenulis.setText(article.getPenulis());
                    tIsi.setText(article.getIsi());
                    Glide.with(getContext()).load(retroserver.url()+article.getFoto())
                            .into(iFoto);
                    if (articles.size() > 0){
                        mAdapter = new AdapterArticle(getContext(),articles);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Ada kesalahan", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ResponseModelArticle> call, Throwable t) {
                Toast.makeText(getContext(),"Ada kesalahan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


//        final TextView textView = root.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}