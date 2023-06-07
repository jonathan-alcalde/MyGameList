package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.List;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;
import pojosmygamelist.Juego;
import pojosmygamelist.Lista;
import pojosmygamelist.Usuario;

public class JuegoEnListaPersonal extends AppCompatActivity {
    private Lista lista;
    ImageView imagenJuego;
    TextView nombreJuego;
    EditText estadoEditText;
    EditText horasjugadasText;
    EditText puntuacionText;
    Button eliminarLista;
    Button actualizarLista;
    UserSingleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_listapersonal);
        imagenJuego = findViewById(R.id.imagenJuego);
        nombreJuego = findViewById(R.id.nombreJuego);
        estadoEditText = findViewById(R.id.editEstado);
        horasjugadasText =findViewById(R.id.editHorasJugadas);
        puntuacionText = findViewById(R.id.editPuntuacion);
        eliminarLista = findViewById(R.id.eliminarLista);
        actualizarLista = findViewById(R.id.actualizarLista);

        singleton = UserSingleton.getInstance();
        Intent intent = getIntent();
        lista = (Lista) intent.getSerializableExtra("juegoSeleccionado");

        // Cargar la imagen desde la URL utilizando Picasso
        Picasso.get().load(lista.getImagen()).into(imagenJuego);

        estadoEditText.setText(lista.getEstado());
        horasjugadasText.setText(Integer.parseInt(String.valueOf(lista.getHorasJugadas())));
        puntuacionText.setText(Integer.parseInt(String.valueOf(lista.getPuntuacion())));

        eliminarLista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Crear y ejecutar el hilo en segundo plano
                EliminarListaTask hilo = new EliminarListaTask();
                hilo.execute();
                //Crear un objeto Intent para la actividad de destino
                Intent intent = new Intent(JuegoEnListaPersonal.this, ListaPersonal.class);
                startActivity(intent);
            }
        });

        actualizarLista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(Integer.parseInt(puntuacionText.getText().toString()) > 100 || Integer.parseInt(puntuacionText.getText().toString()) < 0){
                    Snackbar.make(v, "La puntuacion debe ser un numero entre 0 y 100", Snackbar.LENGTH_SHORT).show();
                }
                else{

                    // Crear y ejecutar el hilo en segundo plano
                    ActualizarListaTask hilo = new ActualizarListaTask();
                    hilo.execute();
                }
            }
        });
    }

    private class EliminarListaTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Crear una instancia de la conexión a la base de datos
                CADMyGameList cad = new CADMyGameList();

                // el juego seleccionado
                Lista lista = (Lista) getIntent().getSerializableExtra("juegoSeleccionado");



                // Eliminar la lista en la base de datos
                cad.eliminardeLista(lista.getId());

                return true;
            } catch (ExcepcionMyGameList | SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            View view = findViewById(android.R.id.content);
            if (success) {
                // Acción exitosa: Actualizar la interfaz de usuario o mostrar un mensaje
                Snackbar.make(view, "La lista se ha eliminado correctamente", Snackbar.LENGTH_SHORT).show();
            } else {
                // Acción fallida: Mostrar un mensaje de error o realizar alguna acción apropiada
                Snackbar.make(view, "Error al eliminar la lista", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private class ActualizarListaTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Crear una instancia de la conexión a la base de datos
                CADMyGameList cad = new CADMyGameList();

                // el juego seleccionado
                Lista lista = (Lista) getIntent().getSerializableExtra("juegoSeleccionado");
                lista.setEstado(estadoEditText.getText().toString());
                lista.setHorasJugadas(Integer.parseInt(horasjugadasText.getText().toString()));
                lista.setPuntuacion(Integer.parseInt(puntuacionText.getText().toString()));


                // Eliminar la lista en la base de datos
                cad.actualizarLista(lista);

                return true;
            } catch (ExcepcionMyGameList | SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            View view = findViewById(android.R.id.content);
            if (success) {
                // Acción exitosa: Actualizar la interfaz de usuario o mostrar un mensaje
                Snackbar.make(view, "La lista se ha actualizado correctamente", Snackbar.LENGTH_SHORT).show();
            } else {
                // Acción fallida: Mostrar un mensaje de error o realizar alguna acción apropiada
                Snackbar.make(view, "Error al actualizar la lista", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}