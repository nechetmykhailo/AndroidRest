package com.example.mixazp.androidrest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mixazp.androidrest.adapter.RecyclerAdapterFinalProduct;
import com.example.mixazp.androidrest.model.FinalProduct;
import com.example.mixazp.androidrest.retrofit.ApiService;
import com.example.mixazp.androidrest.retrofit.RetroClient;
import com.example.mixazp.androidrest.model.ReviewRequest;
import com.example.mixazp.androidrest.model.ReviewResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {

    private static final int KEY_REG = 1;
    private TextView tvProductName;
    private TextView tvProductTitle;
    private ImageView imageProduct;
    private RatingBar ratingBar;
    private EditText etOtziv;
    private Button btnRegistration;
    private Button btnPost;
    private RecyclerView recyclerViewProduct;
    private List<FinalProduct> posts;

    private int raitingBarInt; // рейтинг отзыва
    private Integer intID; // id продукта
    private String loginReg; // логин который пришел после авторизаций

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mixazp.androidrest.R.layout.activity_product);

        tvProductName = findViewById(com.example.mixazp.androidrest.R.id.tvProductName);
        tvProductTitle = findViewById(com.example.mixazp.androidrest.R.id.tvProductTitle);
        imageProduct = findViewById(com.example.mixazp.androidrest.R.id.imageProduct);
        ratingBar = findViewById(com.example.mixazp.androidrest.R.id.ratingBar);
        etOtziv = findViewById(com.example.mixazp.androidrest.R.id.etOtziv);
        btnPost = findViewById(com.example.mixazp.androidrest.R.id.btnPost);
        btnRegistration = findViewById(com.example.mixazp.androidrest.R.id.btnRegistration);
        recyclerViewProduct = findViewById(com.example.mixazp.androidrest.R.id.recyclerViewProduct);

        posts = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        String title = extras.getString("tvName");
        String text = extras.getString("tvTitle");
        String image = extras.getString("image");
        intID = extras.getInt("tvID");


        tvProductName.setText(text);
        tvProductTitle.setText(title);

        Picasso.with(ProductActivity.this)
                .load(image)
                .into(imageProduct);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
                raitingBarInt = (int) v;

                Log.d("LOL", "RAITING BAR " + raitingBarInt);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewProduct.setLayoutManager(layoutManager);
        RecyclerAdapterFinalProduct adapter = new RecyclerAdapterFinalProduct(this, posts);
        recyclerViewProduct.addItemDecoration(new SpacecItemDecoration(getApplicationContext()));
        recyclerViewProduct.setAdapter(adapter);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), RegistrationActivity.class);
                startActivityForResult(intent, KEY_REG);
            }
        });

        productAdapter(); // отображение списка отзывов

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retroReview();
            }
        });

    }

    private void retroReview() {
        if (loginReg!=null) {

            ReviewRequest reviewRequest = new ReviewRequest();

            final String review = etOtziv.getText().toString();
            Log.d("LOL", "EDITTEXT " + review);

            reviewRequest.setRate(raitingBarInt);
            reviewRequest.setText(review);

            ApiService api = RetroClient.getApiService();
            Call<ReviewResponse> reviewCall = api.reviewRequest(reviewRequest, intID);

            reviewCall.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {

                    if(review.equals("") ){
                        Toast.makeText(getApplicationContext(), "Поле должно быть заполненно", Toast.LENGTH_SHORT).show();
                    }else if(raitingBarInt==0){
                        Toast.makeText(getApplicationContext(), "Оставте рейтинг", Toast.LENGTH_SHORT).show();
                    }else {
                        if(response.isSuccessful()){
                            Log.d("MyTag", "RESPONCE REVIEW " + response.toString());
                        }else {
                            Log.d("MyTag", "NOT SUCCESSFUL RESPONCE REVIEW " + response.toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {
                    Log.d("MyTag", "call REVIEW " + call.toString());
                    Log.d("MyTag", "t REVIEW " + t.toString());
                }
            });
        } else {
            Toast.makeText(getApplication(), "Что бы оставить отзыв, Вы должны авторизироваться", Toast.LENGTH_SHORT).show();
        }
    }

    private void productAdapter() {
        ApiService api = RetroClient.getApiService();
        Call<ArrayList<FinalProduct>> call = api.getMyProduct(intID);

        call.enqueue(new Callback<ArrayList<FinalProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<FinalProduct>> call, Response<ArrayList<FinalProduct>> response) {
                Log.d("MyTag", response.toString());

                posts.addAll(response.body());
                recyclerViewProduct.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<FinalProduct>> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == KEY_REG) {
            if (resultCode == RegistrationActivity.RESULT_OK) {
                loginReg = data.getStringExtra("login"); //получил логин после авторизаций
                Log.d("MyTag", "login " + loginReg);

                btnRegistration.setEnabled(false);
            }
        }
    }
}
