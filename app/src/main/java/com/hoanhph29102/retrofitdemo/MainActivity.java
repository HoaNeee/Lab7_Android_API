package com.hoanhph29102.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.hoanhph29102.retrofitdemo.retrofit.ApiService;
import com.hoanhph29102.retrofitdemo.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText edTen, edMa;
    Button btnInsert, btnSelect;

    TextView tvKQ;
    List<SinhVien> list;
    String kq = "";

    SinhVienAdapter sinhVienAdapter;
    RecyclerView rcSV;
    @SerializedName("masv")
    String masv;
    @SerializedName("tensv")
    String tensv;

    SinhVien sinhVien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edMa = findViewById(R.id.ed_ma);
        edTen = findViewById(R.id.ed_ten);
        btnInsert = findViewById(R.id.btn_insert);

        btnSelect = findViewById(R.id.btn_select);
        //tvKQ = findViewById(R.id.tv_kq);
        rcSV = findViewById(R.id.rc_sinh_vien);

        btnInsert.setOnClickListener(v -> insertRetrofit());
        btnSelect.setOnClickListener(view -> getAllSV());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcSV.setLayoutManager(linearLayoutManager);
    }

    public void getAllSV(){

        ApiService apiService = RetrofitClient.getRetrofit().create(ApiService.class);
        Call<List<SinhVien>> call = apiService.getAllSV();

        call.enqueue(new Callback<List<SinhVien>>() {
            @Override
            public void onResponse(Call<List<SinhVien>> call, Response<List<SinhVien>> response) {
                if (response.isSuccessful()){
                    list = response.body();

//                    for (SinhVien t : listSV){
//                        kq += "tên : " + t.getTensv()+";" +"mã: " + t.getMasv()+"\n";
//                    }
//                    tvKQ.setText(kq);

                    sinhVienAdapter = new SinhVienAdapter(MainActivity.this,list, MainActivity.this);
                    rcSV.setAdapter(sinhVienAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "GET khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SinhVien>> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Main", "err"+ t.getMessage());
            }
        });
    }

    private void insertRetrofit(){

        masv = edMa.getText().toString();
        tensv = edTen.getText().toString();

        sinhVien = new SinhVien(masv,tensv);

        ApiService apiService= RetrofitClient.getRetrofit().create(ApiService.class);

        Call<SvRespon> call = apiService.insert(masv,tensv);

        call.enqueue(new Callback<SvRespon>() {
            @Override
            public void onResponse(Call<SvRespon> call, Response<SvRespon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SvRespon svRespon = response.body();
                    Toast.makeText(MainActivity.this, ""+svRespon.getMessage(), Toast.LENGTH_SHORT).show();
                    getAllSV();
                } else {
                    // Xử lý trường hợp phản hồi không hợp lệ hoặc không có dữ liệu
                    Toast.makeText(MainActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SvRespon> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("err","err" + t.getMessage());
            }
        });
    }
}