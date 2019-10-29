package com.example.apps1m.diariorm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> textos;
    ListView lista;
    ArrayAdapter adapter;
    Context context;
    int posicion;
    SharedPreferences _sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        lista = findViewById(R.id.lista);


        _sharedPreferences = getSharedPreferences("entradaDelUsuario", MODE_PRIVATE);

        // Datos
        textos = new ArrayList<>();
        textos = recuperar(); // de esta forma se recuperan los datos de la ultima vez llamando al metodo de recuperar.

        // Adapter para la vista de los datos
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, textos);
        lista.setAdapter(adapter);


        // Al hacer click en una entrada la visualizas en la siguiente patalla
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicion = position;

                Intent intent = new Intent(context, Main2Activity.class);

                String  cogerText = textos.get(position);
                intent.putExtra("aparecerTexto", cogerText);

                startActivityForResult(intent, 2);

            }
        });
    }


   /* Memoria Start */
    public void anadir(View view) {
        Intent intent = new Intent(context, Main2Activity.class);
        startActivityForResult(intent, 1);
    }

    // guardar datos
    private void guardar(ArrayList<String> entradas) {
        SharedPreferences.Editor editor = _sharedPreferences.edit();

        for (int i = 0; i < entradas.size(); i++) {

            editor.putString("texto" + i, entradas.get(i));

        }

        editor.putInt("longitud", entradas.size()); // Se guarda la longitud de la lista
        editor.commit();
    }
    // recuperar datos
    private ArrayList<String> recuperar() {

        ArrayList<String> datos = new ArrayList<>();

        // Saber el tamaño de la lista
        int tamano = _sharedPreferences.getInt("longitud", 0);

        // Coger los datos
        for (int i = 0; i < tamano; i++) {

            String texto = _sharedPreferences.getString("texto" + i, "");
            datos.add(texto);

        }

        return datos;

    }
    /* Final Memoria */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { //con este request añades una nueva entrada
            if (resultCode == Activity.RESULT_OK) {
                String resultadoNuevaEntrada = data.getStringExtra("nuevaEntrada");
                textos.add(resultadoNuevaEntrada);
                adapter.notifyDataSetChanged();
            }
        }
        if(requestCode ==2){ // con este recuest editas las entradas ya guardadas
            if (resultCode == Activity.RESULT_OK) {

                String editarEntrada = data.getStringExtra("nuevaEntrada");
                textos.set(posicion, editarEntrada);
                adapter.notifyDataSetChanged();
            }

        }
        guardar(textos);
    }
}
