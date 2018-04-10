package com.example.ainhoa.app;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuHistoriasActivity extends AppCompatActivity {
    User user;
    ObjectsApplication objects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_historias);
        objects = (ObjectsApplication)getApplication();

        //TODO ESTO SOLO VALE PARA LOCAL.
        objects.usuario=new User();
        user = objects.usuario;
        //TODO Pasar el Id de la historia clickada.
        ListView listView = findViewById(R.id.listHistorias);
        ArrayList<String> historias = user.getHistorias();

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historias);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MenuHistoriasActivity.this, HistoriaActivity.class);
                //TODO NO HAY QUE MANDAR UNA HISTORIA NUEVA, SINO LA SELECCIONADA MANDANDO PETICION AL SERVIDOR CON EL IDHISTORIA SELECCIONADO.
                objects.historia = new Historia();
                startActivity(intent);
            }
        });
    }
    
}


