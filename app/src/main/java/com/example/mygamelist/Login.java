package com.example.mygamelist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;
import pojosmygamelist.Usuario;

//import pojosmygamelist.CADMyGameList;
//import pojosmygamelist.ExcepcionMyGameList;
//import pojosmygamelist.Usuario;

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
                new GetRecordInfoTask().execute();
                System.out.println("Entro en el hilo mundo!");
                //try {
                //    CADMyGameList cad = new CADMyGameList();

                //   Usuario usuario = null;


                //    EditText loginusuario = (EditText) findViewById(R.id.usuarioLogin);
                //   EditText logincontraseña = (EditText) findViewById(R.id.usuarioLogin);
                //    System.out.println("loginusuario: " + loginusuario);
                //    System.out.println("logincontraseña: " + logincontraseña);
                //    usuario = cad.login(loginusuario.getText().toString(), logincontraseña.getText().toString());
                //    System.out.println("los datos del usuario son: " + loginusuario);
                   //if(usuario != null){
                         //Crear un objeto Intent para la actividad de destino
                   //     Intent intent = new Intent(Login.this, MenuPrincipal.class);

                         //Iniciar la actividad de destino
                   //     startActivity(intent);
                   // }

                //} catch (ExcepcionMyGameList | SQLException e) {
                //    Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                //  throw new RuntimeException(e);
               //}

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
            }
            catch (ExcepcionMyGameList e) { e.printStackTrace(); }

            Usuario u1 = new Usuario();

            EditText loginusuario = (EditText) findViewById(R.id.usuarioLogin);
            EditText logincontraseña = (EditText) findViewById(R.id.contraseñaLogin);

           try {
               u1.setNombre(loginusuario.getText().toString());
               u1.setContrasena(logincontraseña.getText().toString());
               u1 =  cad.login(u1.getNombre(),u1.getContrasena());
               UserSingleton.usuario = u1;


           }


            catch (ExcepcionMyGameList e) { e.printStackTrace(); }
            catch (SQLException | NoSuchAlgorithmException e) { e.printStackTrace(); }

            if(u1 != null){
                System.out.println("usuario logeado correctamente: " + u1.getNombre());
                if (u1.getIdRol() == 1){
                    //Crear un objeto Intent para la actividad de destino
                    Intent intent = new Intent(Login.this, MenuPrincipal.class);
                    intent.putExtra("usuario", u1.getId());
                    startActivity(intent);
                    //Iniciar la actividad de destino    
                } else if (u1.getIdRol() == 3) {
                    //Crear un objeto Intent para la actividad de destino
                    Intent intent = new Intent(Login.this, MenuAdmin.class);
                    startActivity(intent);
                    //Iniciar la actividad de destino
                }


            }else {
                System.out.println("usuario incorrecto");
                //Toast.makeText(Login.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                Snackbar.make(
                        findViewById(R.id.activity_login),
                        "Usuario incorrecto, revisa los datos o registrate",
                        BaseTransientBottomBar.LENGTH_SHORT
                ).show();
            }
            return recordInfo;



        }
        @Override
        protected void onPostExecute(RecordInfo recordInfo) {
            // Actualiza la interfaz de usuario con la información del registro
            System.out.println("HE ACABADO OK EN EL LOGIN");
        }
    }
    private static class RecordInfo {

        private int id;
        private String name;
        private String email;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

    }
}