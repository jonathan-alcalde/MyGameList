package com.example.mygamelist;

import java.io.Serializable;

public class Juego implements Serializable {

    private String nombre;
    private String desarrolladora;
    private String descripcion;
    private String genero;
    private String plataforma;
    private int image;

    public Juego(String nombre, String desarrolladora, String descripcion, String genero, String plataforma, int image) {
        this.nombre = nombre;
        this.desarrolladora = desarrolladora;
        this.descripcion = descripcion;
        this.genero = genero;
        this.plataforma = plataforma;
        this.image = image;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
