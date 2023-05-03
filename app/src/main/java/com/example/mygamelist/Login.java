package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button botonRegistrar = findViewById(R.id.boton_sign);
        Button botonLogin = findViewById(R.id.boton_login);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Crear un objeto Intent para la actividad de destino
                Intent intent = new Intent(Login.this, Registro.class);

                // Iniciar la actividad de destino
                startActivity(intent);
            }
        });


        botonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Crear un objeto Intent para la actividad de destino
                Intent intent = new Intent(Login.this, MenuPrincipal.class);

                // Iniciar la actividad de destino
                startActivity(intent);
            }
        });

    }
}