package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class JuegoEnMenu extends AppCompatActivity {

    private ImageView imageView;
    private TextView nombreJuego;
    private TextView nombreDesarrolladora;
    private TextView nombreDescripcion;
    private TextView nombreGenero;
    private TextView nombrePlataforma;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_en_menu);



        imageView = findViewById(R.id.imagenJuego);

        // Recibir el recurso de imagen seleccionado del Intent


        // Establecer la imagen en la ImageView


        Intent intent = getIntent();
        Juego juego = (Juego) intent.getSerializableExtra("juegoSeleccionado");

        imageView.setImageResource(juego.getImage());


        nombreJuego = findViewById(R.id.nombreJuego);
        // Establecer el texto de nombre
        nombreJuego.setText(juego.getNombre());

        nombreDesarrolladora = findViewById(R.id.desarrolladoraJuego);
        nombreDesarrolladora.setText(juego.getDesarrolladora());

        nombreDescripcion = findViewById(R.id.descripcionJuego);
        nombreDescripcion.setText(juego.getDescripcion());

        nombreGenero = findViewById(R.id.generoJuego);
        nombreGenero.setText(juego.getGenero());

        nombrePlataforma = findViewById(R.id.plataformaJuego);
        nombrePlataforma.setText(juego.getPlataforma());



    }
}