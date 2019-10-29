package com.example.apps1m.diariorm;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends Activity {

    EditText nuevo;
    Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Sirve para cancelar la entrada y volver a la pagina anterior
        cancelar = findViewById(R.id.hola);
        cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }

        });

        //Pasa el texto al EditText
        nuevo = findViewById(R.id.nuevo);
        Intent intent = getIntent();
        String aparecerTexto= intent.getStringExtra("aparecerTexto");
        nuevo.setText(aparecerTexto);

    }

    public void añadir(View view) {
        Intent intent = new Intent();

        intent.putExtra("nuevaEntrada", nuevo.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}

