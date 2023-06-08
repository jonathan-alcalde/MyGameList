package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pojosmygamelist.Lista;

public class JuegoEnListaAjena extends AppCompatActivity {
    private Lista lista;
    ImageView imagenJuego;
    TextView nombreJuego;
    TextView estadoEditText;
    TextView horasjugadasText;
    TextView puntuacionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_en_lista_ajena);
        imagenJuego = findViewById(R.id.imagenJuego);
        nombreJuego = findViewById(R.id.nombreJuego);
        estadoEditText = findViewById(R.id.editEstado);
        horasjugadasText = findViewById(R.id.editHorasJugadas);
        puntuacionText = findViewById(R.id.editPuntuacion);

        Intent intent = getIntent();
        lista = (Lista) intent.getSerializableExtra("juegoSeleccionado");

        // Cargar la imagen desde la URL utilizando Picasso
        Picasso.get().load(lista.getImagen()).into(imagenJuego);
        nombreJuego.setText(String.valueOf(lista.getNombreJuego()));
        if(lista.getEstado() == null){
                estadoEditText.setText("No empezado");
        }else{
            estadoEditText.setText(String.valueOf(lista.getEstado()), TextView.BufferType.EDITABLE);
        }

        horasjugadasText.setText(String.valueOf(lista.getHorasJugadas()), TextView.BufferType.EDITABLE);
        puntuacionText.setText(String.valueOf(lista.getPuntuacion()), TextView.BufferType.EDITABLE);
    }
}