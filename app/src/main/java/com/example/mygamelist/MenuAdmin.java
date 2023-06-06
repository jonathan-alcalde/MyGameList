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

    private class GetRecordInfoTask extends AsyncTask<String, Void, Boolean> {
        private String nombreUsuarioInput;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                CADMyGameList cad = new CADMyGameList();
                EditText nombreUsuario = findViewById(R.id.nombreUsuario);
                nombreUsuarioInput = nombreUsuario.getText().toString();
                return cad.eliminarUsuarioPorNombre(nombreUsuarioInput);
            } catch (ExcepcionMyGameList e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Snackbar.make(
                        findViewById(R.id.activity_menu_admin),
                        "Usuario " + nombreUsuarioInput +" eliminado correctamente",
                        BaseTransientBottomBar.LENGTH_SHORT
                ).show();
            } else {
                Snackbar.make(
                        findViewById(R.id.activity_menu_admin),
                        "El usuario " + nombreUsuarioInput +" no existe",
                        BaseTransientBottomBar.LENGTH_SHORT
                ).show();
                EditText nombreUsuario = findViewById(R.id.nombreUsuario);
                nombreUsuario.setText(nombreUsuarioInput);
            }
        }
    }
}
