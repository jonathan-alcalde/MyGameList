package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.Desarrolladora;
import pojosmygamelist.ExcepcionMyGameList;
import pojosmygamelist.Genero;
import pojosmygamelist.Juego;
import pojosmygamelist.Lista;
import pojosmygamelist.Plataforma;
import pojosmygamelist.Usuario;

public class JuegoEnMenu extends AppCompatActivity {

    private ImageView imageView;
    private TextView nombreJuego;
    private TextView juegopuntuacion;
    private TextView nombreDesarrolladora;
    private TextView nombreDescripcion;
    private TextView nombreGenero;
    private TextView nombrePlataforma;

    private Button botonAñadir;

    private Juego juego;
    private ArrayList<Plataforma> plataformas;
    private int plataformaSeleccionadaId = -1;

    private UserSingleton userSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_menuprincipal);

        imageView = findViewById(R.id.imagenJuego);
        nombreJuego = findViewById(R.id.nombreJuego);
        nombreDesarrolladora = findViewById(R.id.desarrolladoraJuego);
        nombreDescripcion = findViewById(R.id.descripcionJuego);
        nombreGenero = findViewById(R.id.generoJuego);
        juegopuntuacion = findViewById(R.id.estado);
        botonAñadir = findViewById(R.id.addLista);

        userSingleton = UserSingleton.getInstance();
        Usuario usuario = userSingleton.getUsuario();

        Intent intent = getIntent();
        juego = (Juego) intent.getSerializableExtra("juegoSeleccionado");

        // Cargar la imagen desde la URL utilizando Picasso
        Picasso.get().load(juego.getImagen()).into(imageView);

        nombreJuego.setText(juego.getNombreJuego());
        nombreDescripcion.setText(juego.getDescripcion());
        juegopuntuacion.setText("Puntuacion: " + juego.getPuntuacion_global());

        // Configurar el campo de género
        ArrayList<Genero> generos = juego.getGeneros();
        String generosTexto = obtenerTextoDesdeListaGeneros(generos);
        nombreGenero.setText(generosTexto);

        // Configurar el campo de desarrolladora
        ArrayList<Desarrolladora> desarrolladoras = juego.getDesarrolladoras();
        String desarrolladorasTexto = obtenerTextoDesdeListaDesarrolladoras(desarrolladoras);
        nombreDesarrolladora.setText(desarrolladorasTexto);

        // Configurar el campo de plataforma
        plataformas = juego.getPlataformas();
//        mostrarDialogoPlataformas();


        botonAñadir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Crear y ejecutar el hilo en segundo plano
                InsertarListaTask hilo = new InsertarListaTask();
                hilo.execute();
            }
        });
    }

    private String obtenerTextoDesdeListaGeneros(ArrayList<Genero> lista) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i).getNombre().toString());
            if (i < lista.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private String obtenerTextoDesdeListaDesarrolladoras(ArrayList<Desarrolladora> lista) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lista.size(); i++) {
            sb.append(lista.get(i).getNombre().toString());
            if (i < lista.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private class InsertarListaTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Crear una instancia de la conexión a la base de datos
                CADMyGameList cad = new CADMyGameList();

                // Obtener el usuario y el juego seleccionados
                Usuario usuario = userSingleton.getUsuario();
                Juego juego = (Juego) getIntent().getSerializableExtra("juegoSeleccionado");

                // Verificar si el objeto Usuario es nulo
                if (usuario == null) {
                    return false; // Mostrar error o realizar alguna acción apropiada
                }

                // Crear una nueva lista
                Lista lista = new Lista();
                lista.setId_usuario(usuario.getId());
                lista.setIdJuego(juego.getIdJuego());

                // Insertar la lista en la base de datos
                cad.insertarLista(lista);

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
                Snackbar.make(view, "La lista se ha insertado correctamente", Snackbar.LENGTH_SHORT).show();
            } else {
                // Acción fallida: Mostrar un mensaje de error o realizar alguna acción apropiada
                Snackbar.make(view, "Error al insertar la lista", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
