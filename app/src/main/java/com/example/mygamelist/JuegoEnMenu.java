package com.example.mygamelist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pojosmygamelist.Desarrolladora;
import pojosmygamelist.Genero;
import pojosmygamelist.Juego;
import pojosmygamelist.Plataforma;

public class JuegoEnMenu extends AppCompatActivity {

    private ImageView imageView;
    private TextView nombreJuego;
    private TextView juegopuntuacion;
    private TextView nombreDesarrolladora;
    private TextView nombreDescripcion;
    private TextView nombreGenero;
    private TextView nombrePlataforma;

    private Juego juego;
    private ArrayList<Plataforma> plataformas;
    private int plataformaSeleccionadaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_en_menu);

        imageView = findViewById(R.id.imagenJuego);
        nombreJuego = findViewById(R.id.nombreJuego);
        nombreDesarrolladora = findViewById(R.id.desarrolladoraJuego);
        nombreDescripcion = findViewById(R.id.descripcionJuego);
        nombreGenero = findViewById(R.id.generoJuego);
        nombrePlataforma = findViewById(R.id.plataformaJuego);
        juegopuntuacion = findViewById(R.id.puntuacion);

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
        mostrarDialogoPlataformas();
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

    private void mostrarDialogoPlataformas() {
        CharSequence[] opciones = new CharSequence[plataformas.size()];
        final int[] plataformaIds = new int[plataformas.size()];

        for (int i = 0; i < plataformas.size(); i++) {
            opciones[i] = plataformas.get(i).getNombre();
            plataformaIds[i] = plataformas.get(i).getIdPlataforma();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una plataforma");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Plataforma plataformaSeleccionada = plataformas.get(which);
                plataformaSeleccionadaId = plataformaIds[which];
                nombrePlataforma.setText(plataformaSeleccionada.getNombre());
                // Realizar cualquier acción necesaria con la plataforma seleccionada
            }
        });
        builder.show();
    }
}

