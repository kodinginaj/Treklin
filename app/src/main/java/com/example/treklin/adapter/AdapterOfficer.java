package com.example.treklin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treklin.R;
import com.example.treklin.api.ApiRequest;
import com.example.treklin.api.Retroserver;
import com.example.treklin.model.ArticleModel;
import com.example.treklin.model.OfficerModel;
import com.example.treklin.model.ResponseModel;
import com.example.treklin.util.Session;
import com.google.android.gms.common.api.Api;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterOfficer extends RecyclerView.Adapter<AdapterOfficer.TampungData> {

    private Context ctx;
    private List<OfficerModel> listOfficer;
    private String jarakDigit;
    private Session session;

    private Button btnKomplain;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

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

        session = new Session(ctx);
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(Double.parseDouble(session.getLatitude()));
        startPoint.setLongitude(Double.parseDouble(session.getLongitude()));

        Location endPoint=new Location("locationB");
        endPoint.setLatitude(Double.parseDouble(dataOfficer.getLatitude()));
        endPoint.setLongitude(Double.parseDouble(dataOfficer.getLongitude()));

        float distance = startPoint.distanceTo(endPoint)/1000;
        if(distance<10) {
            jarakDigit = Double.toString(distance).substring(0, 4);
        }else{
            jarakDigit = Double.toString(distance).substring(0, 5);
        }

        holder.etNama.setText("Nama : "+dataOfficer.getNama());
        holder.etJarak.setText("Jarak : "+jarakDigit+" KM");
        holder.etPeralatan.setText("Peralatan : "+dataOfficer.getPeralatan());
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

            btnKomplain = v.findViewById(R.id.btnKomplain);

            btnKomplain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogForm();
                }
            });
        }

        private void DialogForm() {
            dialog = new AlertDialog.Builder(ctx);
            inflater = LayoutInflater.from(ctx);
            dialogView = inflater.inflate(R.layout.modal_dialog, null);
            dialog.setView(dialogView);
            dialog.setCancelable(true);

            EditText komplain = dialogView.findViewById(R.id.etKomplain);

            dialog.setPositiveButton("LAPOR", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Session session = new Session(ctx);
                    String id_user = session.getId();

                    ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                    Call<ResponseModel> userComplaint = api.userComplaint(id_user,dataOfficer.getId(),komplain.getText().toString());
                    userComplaint.enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            });

            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }


}
