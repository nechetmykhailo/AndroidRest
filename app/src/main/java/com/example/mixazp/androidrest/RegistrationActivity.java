package com.example.mixazp.androidrest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mixazp.androidrest.model.RegistrationRequest;
import com.example.mixazp.androidrest.model.RegistrationResponse;
import com.example.mixazp.androidrest.retrofit.ApiService;
import com.example.mixazp.androidrest.retrofit.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etPassword;
    private Button btnRegOk;

    private String login;
    private String password;

    private final ApiService api = RetroClient.getApiService();
    private final RegistrationRequest request = new RegistrationRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.mixazp.androidrest.R.layout.activity_registration);

        etLogin = findViewById(com.example.mixazp.androidrest.R.id.etLogin);
        etPassword = findViewById(com.example.mixazp.androidrest.R.id.etPass);
        btnRegOk = findViewById(com.example.mixazp.androidrest.R.id.btnRegOk);

        btnRegOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg();
            }
        });
    }

    private void reg() {

        login = etLogin.getText().toString();
        password = etPassword.getText().toString();

        request.setUsername(login);
        request.setPassword(password);

        Call<RegistrationResponse> call = api.registerUser(request);
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse( Call<RegistrationResponse> call, Response<RegistrationResponse> response) {

                String s = response.body().getToken();
                Log.d("MyTag", "GETTOKEN " + s);
                if (s==null) {
                    if(response.isSuccessful()){
                        Log.d("MyTag", "onResponse response " + response.toString());

                        Intent intent = new Intent(RegistrationActivity.this, ProductActivity.class);
                        intent.putExtra("login", login);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else {
                        switch (response.code()) {
                            case 404:
                                Toast.makeText(RegistrationActivity.this, "not found", Toast.LENGTH_SHORT).show();
                                break;
                            case 500:
                                Toast.makeText(RegistrationActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(RegistrationActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                } else {
                    auth();
                }
            }
            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
            }
        });
    }

    private void auth() {
        Call<RegistrationResponse> callAuth = api.autorizationUser(request);

        callAuth.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(RegistrationActivity.this, ProductActivity.class);
                    intent.putExtra("login", login);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "NotGood Registration ", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {

            }
        });
    }
}
