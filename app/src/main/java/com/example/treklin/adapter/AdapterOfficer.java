package com.example.treklin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treklin.R;
import com.example.treklin.model.ArticleModel;
import com.example.treklin.model.OfficerModel;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterOfficer extends RecyclerView.Adapter<AdapterOfficer.TampungData> {

    private Context ctx;
    private List<OfficerModel> listOfficer;

    public AdapterOfficer(Context ctx, List<OfficerModel> listOfficer){
        this.ctx = ctx;
        this.listOfficer = listOfficer;
    }


    @NonNull
    @Override
    public TampungData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        TampungData tampungData = new TampungData(layout);
        return tampungData;
    }

    @Override
    public void onBindViewHolder(@NonNull TampungData holder, int position) {

        OfficerModel dataOfficer = listOfficer.get(position);
        holder.etNama.setText(dataOfficer.getNama());
        holder.etJarak.setText("0 KM");
        holder.etPeralatan.setText(dataOfficer.getPeralatan());

        holder.dataOfficer = dataOfficer;
    }

    @Override
    public int getItemCount() {
        return listOfficer.size();
    }


    class TampungData extends RecyclerView.ViewHolder {

        TextView etNama, etJarak, etPeralatan;
        OfficerModel dataOfficer;

        private TampungData(View v) {
            super(v);

            etNama = v.findViewById(R.id.etNama);
            etJarak = v.findViewById(R.id.etJarak);
            etPeralatan = v.findViewById(R.id.etPeralatan);
        }
    }

}
