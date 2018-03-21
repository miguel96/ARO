package com.example.ainhoa.app;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoriaActivity extends AppCompatActivity {
    Retrofit retrofit;
    HistoriaService historiaService;

    ProgresoHistoria progresoHistoria;
    Historia historia;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        TextView tituloHistoria = findViewById(R.id.txtTituloHistoria);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        historia = new Historia();
        user = (User)bundle.getParcelable("user");
        /*try {
            getHistoria();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //TODO el codigo de aqui debajo hay que modificarlo con los valores que tiene historia.


        tituloHistoria.setText(historia.getTituloHistoria());

        // Funciones del botón
        final Button buttonToMapa = findViewById(R.id.btnToMapa);
        buttonToMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoriaActivity.this, MapActivity.class);
                intent.putExtra("user",(Parcelable)user);
                intent.putExtra("historia",(Parcelable)historia);
                startActivity(intent);
            }
        });

        // display de la historia
        ListView listView = findViewById(R.id.listPistas);
        // fragmentos de la historia

        ArrayList<String> frags = new ArrayList<String>();
        frags.add(historia.getIdHistoria());
        frags.add(historia.getDescripcion());

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, frags);
        listView.setAdapter(adapter);



    }

    private void getHistoria() throws IOException {
        this.retrofit=new Retrofit.Builder()
                .baseUrl("http://gpi2unavarra.hopto.org:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.historiaService = retrofit.create(HistoriaService.class);

        Call<Historia> call = historiaService.getHistoriaById(this.historia.getIdHistoria(), new Callback<Historia>() {
            @Override
            public void onResponse(Call<Historia> call, Response<Historia> response) {
                historia = response.body();
            }

            @Override
            public void onFailure(Call<Historia> call, Throwable t) {

            }
        });

    }

}
