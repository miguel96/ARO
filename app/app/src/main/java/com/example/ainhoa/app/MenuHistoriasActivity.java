package com.example.ainhoa.app;

import android.content.Intent;
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
    Usuario user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_historias);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = (Usuario)bundle.get("usuario");
        ListView listView = findViewById(R.id.listHistorias);
        ArrayList<String> historias = new ArrayList<String>();
        historias.add("Historia1");
        historias.add("Historia2");

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historias);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = ((TextView)view).getText().toString();
                //Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MenuHistoriasActivity.this, HistoriaActivity.class);
                intent.putExtra("selected-item", text);
                startActivity(intent);
            }
        });
    }
    
}


