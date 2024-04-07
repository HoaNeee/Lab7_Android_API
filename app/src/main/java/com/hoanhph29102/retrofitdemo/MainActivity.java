package com.hoanhph29102.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hoanhph29102.retrofitdemo.retrofit.ApiService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText edTen, edMa;
    Button btnInsert, btnUpdate, btnDel, btnSelect;

    TextView tvKQ;
    List<TestModel> list;
    String kq = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edMa = findViewById(R.id.ed_ma);
        edTen = findViewById(R.id.ed_ten);
        btnInsert = findViewById(R.id.btn_insert);
        btnUpdate = findViewById(R.id.btn_update);
        btnDel = findViewById(R.id.btn_delete);
        btnSelect = findViewById(R.id.btn_select);
        tvKQ = findViewById(R.id.tv_kq);

        btnInsert.setOnClickListener(v -> insertRetrofit());
        btnSelect.setOnClickListener(view -> getAllSV());
    }

    private void getAllSV(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.172:3002/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiService apiService= retrofit.create(ApiService.class);
        Call<List<TestModel>> call = apiService.getAllSV();

        call.enqueue(new Callback<List<TestModel>>() {
            @Override
            public void onResponse(Call<List<TestModel>> call, Response<List<TestModel>> response) {
                if (response.isSuccessful()){
                    List<TestModel> svRespon = response.body();


                    for (TestModel t : svRespon){
                        kq += "tên : " + t.getTensv()+";" +"mã: " + t.getMasv()+"\n";
                    }
                    tvKQ.setText(kq);
                }
            }

            @Override
            public void onFailure(Call<List<TestModel>> call, Throwable t) {
                tvKQ.setText(t.getMessage());
                Log.d("Main", "err"+ t.getMessage());
            }
        });
    }

    private void insertRetrofit(){

        TestModel testModel = new TestModel();

        String masv = edMa.getText().toString();
        String tensv = edTen.getText().toString();

        testModel.setTensv(tensv);
        testModel.setMasv(masv);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.172:3002/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiService apiService= retrofit.create(ApiService.class);

        Call<SvRespon> call = apiService.insert(tensv,masv);

        call.enqueue(new Callback<SvRespon>() {
            @Override
            public void onResponse(Call<SvRespon> call, Response<SvRespon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SvRespon svRespon = response.body();
                    tvKQ.setText(svRespon.getMessage());
                } else {
                    // Xử lý trường hợp phản hồi không hợp lệ hoặc không có dữ liệu
                    tvKQ.setText("Không có dữ liệu trả về từ server");
                }
            }

            @Override
            public void onFailure(Call<SvRespon> call, Throwable t) {
                tvKQ.setText(t.getMessage());
                Log.d("err","err" + t.getMessage());
            }
        });
    }
}