package com.example.ainhoa.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button buttonHistorias = findViewById(R.id.btnToHistorias);
        buttonHistorias.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MenuHistoriasActivity.class);
                startActivity(intent);
            }
        });

        /*
        final Button btnUbicaciones = findViewById(R.id.btnMapAdmin);
        buttonHistorias.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MapAdminActivity.class);
                startActivity(intent);
            }
        });
        */
    }
}
