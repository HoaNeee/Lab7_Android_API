package com.hoanhph29102.retrofitdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
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

import com.google.gson.annotations.SerializedName;
import com.hoanhph29102.retrofitdemo.retrofit.ApiService;
import com.hoanhph29102.retrofitdemo.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.viewHolder>{
    Context context;
    List<SinhVien> listSV;

    EditText edMaDetail;
    EditText edTenDetail;
    @SerializedName("masv")
    String newMaSV;
    @SerializedName("tensv")
    String newTenSV;

    private MainActivity mainActivity;

    public SinhVienAdapter(Context context, List<SinhVien> listSV, MainActivity mainActivity) {
        this.context = context;
        this.listSV = listSV;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sinh_vien,parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SinhVien sinhVien =listSV.get(position);
        holder.tvMa.setText(sinhVien.getMasv());
        holder.tvTen.setText(sinhVien.getTensv());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSinhVienDetailDialog(sinhVien);
            }
        });

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSinhVien(sinhVien.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSV.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvMa, tvTen;
        ImageView imgDel;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tv_masv);
            tvTen = itemView.findViewById(R.id.tv_tensv);
            imgDel = itemView.findViewById(R.id.img_del);
        }
    }
    private void showSinhVienDetailDialog(SinhVien sinhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_detail_sv, null);
        builder.setTitle("Chi tiết sinh viên");
        builder.setView(dialogView);

        edMaDetail = dialogView.findViewById(R.id.ed_ma_detail);
        edTenDetail = dialogView.findViewById(R.id.ed_ten_detail);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update_sv_dialog);

        edMaDetail.setText(sinhVien.getMasv());
        edTenDetail.setText(sinhVien.getTensv());

        AlertDialog dialog = builder.create();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSinhVien(sinhVien.get_id());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateSinhVien(String id){
        newMaSV = edMaDetail.getText().toString();
        newTenSV = edTenDetail.getText().toString();

        ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        Call<Void> call = apiService.updateSinhVien(id, new SinhVien(newMaSV, newTenSV));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    mainActivity.getAllSV();
                }
                else {
                    Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "không kết nối được", Toast.LENGTH_SHORT).show();
                Log.e("Update","Update"+t.getMessage());
            }
        });
    }

    private void deleteSinhVien (String id){
        ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);

        Call<Void> call = apiService.deleteSinhVien(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    mainActivity.getAllSV();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Không kết nối được sv", Toast.LENGTH_SHORT).show();
                Log.e("Del","Del"+t.getMessage());
            }
        });
    }

}
