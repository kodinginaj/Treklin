package com.example.treklin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.treklin.R;
import com.example.treklin.api.Retroserver;
import com.example.treklin.model.ArticleModel;

import java.util.List;

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.TampungData> {

    private Context ctx;
    private List<ArticleModel> listArticle;
    Retroserver retroServer;

    public AdapterArticle(Context ctx, List<ArticleModel> listArticle){
        this.ctx = ctx;
        this.listArticle = listArticle;
    }

    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_view,parent,false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArticle.TampungData holder, int position) {
        ArticleModel dataArticle = listArticle.get(position);
        retroServer = new Retroserver();
        holder.tvJudul.setText(dataArticle.getJudul());

        Glide.with(ctx).load(retroServer.url()+dataArticle.getFoto())
                .into(holder.iFoto);
        holder.dataArticle = dataArticle;
    }

    @Override
    public int getItemCount() {
        return listArticle.size();
    }

    class TampungData extends RecyclerView.ViewHolder {

        private TextView tvJudul;
        private ImageView iFoto;
        ArticleModel dataArticle;

        private TampungData(View v) {
            super(v);

            tvJudul = (TextView)v.findViewById(R.id.tvJudulArticle);
            iFoto = (ImageView) v.findViewById(R.id.Article_image);

        }
    }
}
