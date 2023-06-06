package com.example.mygamelist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;

public class MenuAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        Button botonEliminar = findViewById(R.id.boton_eliminacion);

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new GetRecordInfoTask().execute();
            }
        });
    }

    private class GetRecordInfoTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                CADMyGameList cad = new CADMyGameList();
                EditText nombreUsuario = findViewById(R.id.nombreUsuario);
                String nombre = nombreUsuario.getText().toString();
                cad.eliminarUsuarioPorNombre(nombre);
            } catch (ExcepcionMyGameList | SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Snackbar.make(
                    findViewById(R.id.activity_menu_admin),
                    "Usuario eliminado correctamente",
                    BaseTransientBottomBar.LENGTH_SHORT
            ).show();
        }
    }
}