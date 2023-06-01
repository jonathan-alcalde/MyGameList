package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import pojosmygamelist.Juego;

public class ListaPersonal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personal);

        ArrayList<Juego> juegosDeUsuario = new ArrayList<Juego>();
    }
}