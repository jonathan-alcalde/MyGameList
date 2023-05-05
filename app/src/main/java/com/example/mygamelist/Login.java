package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;
import pojosmygamelist.Usuario;

public class Login extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Button botonRegistrar = findViewById(R.id.boton_sign);
        Button botonLogin = findViewById(R.id.boton_login);
        botonRegistrar.setOnClickListener(new View.OnClickListener() {

            //metodo para viajar al registro
            public void onClick(View v) {

                // Crear un objeto Intent para la actividad de destino
                Intent intent = new Intent(Login.this, Registro.class);

                // Iniciar la actividad de destino
                startActivity(intent);
            }
        });


        botonLogin.setOnClickListener(new View.OnClickListener() {



            //metodo para viajar al menu principal
            public void onClick(View v) {
                try {
                    CADMyGameList cad = new CADMyGameList();


                    Usuario usuario = null;


                    EditText loginusuario = (EditText) findViewById(R.id.usuarioLogin);
                    EditText logincontraseña = (EditText) findViewById(R.id.usuarioLogin);
                    usuario = cad.login(loginusuario.getText().toString(), logincontraseña.getText().toString());

                    if(usuario != null){
                        // Crear un objeto Intent para la actividad de destino
                        Intent intent = new Intent(Login.this, MenuPrincipal.class);

                        // Iniciar la actividad de destino
                        startActivity(intent);
                    }

                } catch (ExcepcionMyGameList | SQLException e) {
                    Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                  throw new RuntimeException(e);
               }

            }
        });

    }
}