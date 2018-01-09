package com.example.mixazp.androidrest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mixazp.androidrest.adapter.RecyclerViewAdapter;
import com.example.mixazp.androidrest.model.Product;
import com.example.mixazp.androidrest.retrofit.ApiService;
import com.example.mixazp.androidrest.retrofit.RetroClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Product> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mixazp.androidrest.R.layout.activity_main);

        recyclerView = findViewById(com.example.mixazp.androidrest.R.id.recyclerView);

        posts = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, posts);
        recyclerView.setAdapter(adapter);

        getList2();
    }

    private void getList2() {

        ApiService api = RetroClient.getApiService();
        Call<ArrayList<Product>> call = api.getMyJSON();

        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (response.isSuccessful()) {
                    posts.addAll(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Что то пошло не так!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Exeptions", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
