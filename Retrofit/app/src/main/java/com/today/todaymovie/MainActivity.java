package com.today.todaymovie;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd");

    String baseUrl = "http://www.kobis.or.kr"; //엔드 포인트
    String API_KEY = "4853c58d8612e8ec0735dc008592c5e9"; //키값
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        recyclerView=findViewById(R.id.rv_recyclerview);

        //Retrofit 객체생성
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitInterface retrofitInterface= retrofit.create(RetrofitInterface.class);
        Date date = new Date(new Date().getTime()-(1000*60*60*24));
        retrofitInterface.getBoxOffice(API_KEY,mFormat.format(date)).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                System.out.println(call.request().url());
                String a = response.body().toString();
                System.out.println(a+"여기여기" + mFormat.format(date));


                Map<String,Object> boxOfficeResult= (Map<String, Object>) response.body().get("boxOfficeResult");
                ArrayList<Map<String, Object>> jsonList = (ArrayList) boxOfficeResult.get("dailyBoxOfficeList");
                mAdapter=new MyAdapter(jsonList);

                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }// onCreate()..

    public void click_btn(View view) {

    }

}// MainActivity class..