package com.example.mygamelist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;
import java.util.ArrayList;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;

public class MenuAdmin extends AppCompatActivity {
    AutoCompleteTextView auto;
    private String[] nombres;
    CADMyGameList cad = new CADMyGameList();

    public MenuAdmin() throws ExcepcionMyGameList {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        auto = findViewById(R.id.nombreUsuario);

        ObtenerUsuariosTask obtenerUsuariosTask = new ObtenerUsuariosTask();
        obtenerUsuariosTask.execute();

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
                        "Usuario " + nombreUsuarioInput + " eliminado correctamente",
                        BaseTransientBottomBar.LENGTH_SHORT
                ).show();
            } else {
                Snackbar.make(
                        findViewById(R.id.activity_menu_admin),
                        "El usuario " + nombreUsuarioInput + " no existe",
                        BaseTransientBottomBar.LENGTH_SHORT
                ).show();
                EditText nombreUsuario = findViewById(R.id.nombreUsuario);
                nombreUsuario.setText(nombreUsuarioInput);
            }
        }
    }

    private class ObtenerUsuariosTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> nombres = new ArrayList<>();
            try {
                nombres = cad.obtenerTodosLosNombresDeUsuarios();
            } catch (ExcepcionMyGameList | SQLException e) {
                e.printStackTrace();
            }
            return nombres;
        }

        @Override
        protected void onPostExecute(ArrayList<String> nombres) {
            MenuAdmin.this.nombres = nombres.toArray(new String[nombres.size()]);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(MenuAdmin.this, android.R.layout.simple_dropdown_item_1line, MenuAdmin.this.nombres);
            auto.setThreshold(3);
            auto.setAdapter(adapter);
        }
    }
}
