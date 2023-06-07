package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import pojosmygamelist.Usuario;

public class EditarUsuario extends AppCompatActivity {
EditText editarNombreUsuario;
EditText editarContrasenaActual;
EditText editarContrasenaNueva;
EditText editarConfirmarContrasena;
EditText editarCorreo;
private UserSingleton userSingleton = UserSingleton.getInstance(); // Obtener la instancia del UserSingleton;
    Usuario actUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        editarNombreUsuario = findViewById(R.id.editNombreUsuario);
        editarContrasenaActual = findViewById(R.id.contraActual);
        editarContrasenaNueva = findViewById(R.id.nuevaContra);
        editarConfirmarContrasena = findViewById(R.id.nuevaContra);
        editarCorreo = findViewById(R.id.editEmail);Usuario actUsuario;
        actUsuario = userSingleton.getUsuario();
        editarNombreUsuario.setText(String.valueOf(actUsuario.getNombre()), TextView.BufferType.EDITABLE);
        editarCorreo.setText(String.valueOf(actUsuario.getCorreoElectronico()), TextView.BufferType.EDITABLE);



    }
}