package com.gustavohalm.fisio.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gustavohalm.fisio.Apis.Service;
import com.gustavohalm.fisio.Models.Token;
import com.gustavohalm.fisio.Models.User;
import com.gustavohalm.fisio.R;

import java.net.UnknownServiceException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private User user;
    private Token token;
    private EditText editLogin;
    private EditText editPassword;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editLogin = findViewById(R.id.editLoginUsername);
        editPassword = findViewById(R.id.editLoginPassword);

    }

    public void logar(View v) {
        String username = editLogin.getText().toString();
        String password = editPassword.getText().toString();

        user = new User(username, password);




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fisio.gustavohalm.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        Call<Token> call = service.login(user);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if (response.isSuccessful()) {
                    token = response.body();

                    preferences = getSharedPreferences("token_fisio", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("the_token", token.getToken());
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "Erro, código: " + response.code() , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Falha, mensagem: " + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


}