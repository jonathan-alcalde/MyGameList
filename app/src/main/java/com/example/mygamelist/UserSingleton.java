package com.example.mygamelist;

import pojosmygamelist.Usuario;

public class UserSingleton {
    private static UserSingleton instance;
    public static Usuario usuario;

    private UserSingleton() {}

    public static UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}

