package com.example.ainhoa.app;

import android.content.Intent;
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
    String idHistoria;
    Historia historia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        TextView tituloHistoria = findViewById(R.id.txtTituloHistoria);
        Intent intent = getIntent();
        idHistoria = intent.getStringExtra("idHistoria");
        try {
            getHistoria();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO el codigo de aqui debajo hay que modificarlo con los valores que tiene historia.
        // Propiedades extraidas de intent, pasar parametros entre Activites
        String item = getIntent().getStringExtra("selected-item");
        tituloHistoria.setText("You selected " + item);

        // Funciones del botón
        final Button buttonToMapa = findViewById(R.id.btnToMapa);
        buttonToMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoriaActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        // display de la historia
        ListView listView = findViewById(R.id.listPistas);
        // fragmentos de la historia
        ArrayList<String> frags = new ArrayList<String>();
        frags.add("Esta va a ser la parte 1 de la historia. Probando a ver si hace bien el wrap del texto. Si haces click en mi se abrirá una ventana (o aparecerá un toast largo?) donde aparecera la pista que se dió para descubrir esta parte de la historia");
        frags.add("También el sitio donde se encontraba (solo el nombre del lugar o una opción de ir al mapa tambien??)");
        frags.add("Y básicamente es eso");

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, frags);
        listView.setAdapter(adapter);

        final String pista = "Pista prueba \nPara ver como encaja en el cuadro del mensaje. ¿Solo la pista? ¿Nombre del sitio donde se descubrió? ¿Ubicación? Ya veremos. Y a ver como hago para cerrarlo...";

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final TextView msgPista = (TextView) findViewById(R.id.txtPistaHistoria);
                msgPista.setVisibility(View.VISIBLE);
                msgPista.setText(pista);
                final Button btnCierraPista = findViewById(R.id.btnClosePista);
                btnCierraPista.setVisibility(View.VISIBLE);
                btnCierraPista.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        msgPista.setVisibility(View.INVISIBLE);
                        btnCierraPista.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });


    }

    private void getHistoria() throws IOException {
        this.retrofit=new Retrofit.Builder()
                .baseUrl("http://gpi2unavarra.hopto.org:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.historiaService = retrofit.create(HistoriaService.class);

        Call<Historia> call = historiaService.getHistoriaById(this.idHistoria, new Callback<Historia>() {
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
