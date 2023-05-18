package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;
import pojosmygamelist.Usuario;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        Button botonCrearUsuario = findViewById(R.id.boton_crear_usuario);

        botonCrearUsuario.setOnClickListener(new View.OnClickListener() {



            //metodo para registrar nuevo usuario
            public void onClick(View v) {
                new GetRecordInfoTask().execute();
                System.out.println("Entro en el hilo mundo!");


            }
        });




    }

    private class GetRecordInfoTask extends AsyncTask<String, Void, RecordInfo> {
        @Override protected RecordInfo doInBackground(String... params) {
            System.out.println("Entro en el hilo mundo!");
            RecordInfo recordInfo = new RecordInfo();
            CADMyGameList cad = null;
            try {
                cad = new CADMyGameList();
            } catch (ExcepcionMyGameList e) {
                e.printStackTrace();
            }

            Usuario u1 = new Usuario();

            int usuarioCreado = 0;

            EditText registroUsuario = (EditText) findViewById(R.id.registro_usuario);
            EditText registroCorreo = (EditText) findViewById(R.id.registro_correo);
            EditText registroContraseña = (EditText) findViewById(R.id.registro_contraseña);
            EditText registroContraseñaRep = (EditText) findViewById(R.id.registro_contraseña_repetir);

            String registroUsuarioString = registroUsuario.getText().toString();
            String registroCorreoString = registroCorreo.getText().toString();
            String registroContraseñaString = registroContraseña.getText().toString();
            String registroContraseñaRepString = registroContraseñaRep.getText().toString();

            if (!registroContraseñaString.equals(registroContraseñaRepString)) {
                Snackbar.make(
                        findViewById(R.id.activity_registro),
                        "La contraseña debe de ser igual en ambos casos",
                        BaseTransientBottomBar.LENGTH_SHORT
                ).show();
            } else {

                try {
                    u1.setNombre(registroUsuarioString);
                    u1.setCorreoElectronico(registroCorreoString);
                    u1.setContrasena(registroContraseñaString);

                    usuarioCreado = cad.insertarUsuario(u1);

                    System.out.println("usuario creado :" + usuarioCreado);
                } catch (ExcepcionMyGameList e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (usuarioCreado == 1) {
                    System.out.println("usuario creado correctamente: " + u1.getNombre());

                    Snackbar.make(
                            findViewById(R.id.activity_registro),
                            "Usuario creado correctamente, ahora logueate en la pagina anterior",
                            BaseTransientBottomBar.LENGTH_SHORT
                    ).show();


                    Intent intent = new Intent(Registro.this, Login.class);
                    startActivity(intent);
                }
                return recordInfo;
            }

            return recordInfo;
        }
        @Override
        protected void onPostExecute(RecordInfo recordInfo) {
            // Actualiza la interfaz de usuario con la información del registro
            System.out.println("HE ACABADO OK EN EL REGISTRO");
        }
    }

    private static class RecordInfo {
        private String name;
        private String email;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

    }
}
