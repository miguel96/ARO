package com.app.aro.FindKhana;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class showUsers extends AppCompatActivity {

    Retrofit retrofit;
    UsersService users;
    TextView mytext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        this.retrofit=new Retrofit.Builder()
                .baseUrl("http://gpi2unavarra.hopto.org:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.users = retrofit.create(UsersService.class);
        this.mytext=findViewById(R.id.listaUsuariosVista);
        Call<List<User>> call = users.getUsers();
        call.enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                mytext.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mytext.setText("Error");
            }
        });
    }

}
