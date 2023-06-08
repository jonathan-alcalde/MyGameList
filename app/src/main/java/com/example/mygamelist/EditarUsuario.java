package com.example.mygamelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;
import pojosmygamelist.Lista;
import pojosmygamelist.Usuario;

public class EditarUsuario extends AppCompatActivity {
EditText editarNombreUsuario;
EditText editarContrasenaActual;
EditText editarContrasenaNueva;
EditText editarConfirmarContrasena;
EditText editarCorreo;
String errores;
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

        Button eliminarUsuario = (Button) findViewById(R.id.eliminarUsuario);
        eliminarUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Crear y ejecutar el hilo en segundo plano
                EliminarUsuarioTask hilo = new EliminarUsuarioTask();
                hilo.execute();
                //Crear un objeto Intent para la actividad de destino
                Intent intent = new Intent(EditarUsuario.this, Login.class);
                startActivity(intent);
            }
        });
        Button actualizar = (Button) findViewById(R.id.actualizarUsuario);
            actualizar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Crear y ejecutar el hilo en segundo plano
                    ActualizarUsuarioTask hilo = new ActualizarUsuarioTask();
                    hilo.execute();
                    //Crear un objeto Intent para la actividad de destino


                }
            });

    }

    private class EliminarUsuarioTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Crear una instancia de la conexión a la base de datos
                CADMyGameList cad = new CADMyGameList();

                // Eliminar la lista en la base de datos
                cad.eliminarUsuario(UserSingleton.getInstance().getUsuario().getId());

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
                Snackbar.make(view, "El usuario se ha eliminado correctamente", Snackbar.LENGTH_SHORT).show();
            } else {
                // Acción fallida: Mostrar un mensaje de error o realizar alguna acción apropiada
                Snackbar.make(view, "Error al eliminar el usuario", Snackbar.LENGTH_SHORT).show();
            }
        }
    }


    public static boolean validarCorreoElectronico(String correo) {
        // Patrón para validar el formato del correo electrónico
        String patron = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    public static boolean verificarContrasenaSegura(String contrasena) {
        // Verificar la longitud mínima de la contraseña
        if (contrasena.length() < 8) {
            return false;
        }

        // Verificar la presencia de al menos una letra mayúscula
        if (!contrasena.matches(".*[A-Z].*")) {
            return false;
        }

        // Verificar la presencia de al menos una letra minúscula
        if (!contrasena.matches(".*[a-z].*")) {
            return false;
        }

        // Verificar la presencia de al menos un dígito
        if (!contrasena.matches(".*\\d.*")) {
            return false;
        }

        // Verificar la presencia de al menos un carácter especial
        if (!contrasena.matches(".*[!@#\\$%\\^&\\*()\\-+=~`{}\\[\\]|\\\\:;\"'<>,.?/].*")) {
            return false;
        }



        // La contraseña cumple con todos los criterios de seguridad
        return true;
    }

    private class ActualizarUsuarioTask extends AsyncTask<Void, Void, Boolean> {
        private String errores;

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                // Crear una instancia de la conexión a la base de datos
                CADMyGameList cad = new CADMyGameList();
                actUsuario = userSingleton.getUsuario();
                String nombreUsuario = editarNombreUsuario.getText().toString();
                String actContrasena = editarContrasenaActual.getText().toString();
                String newContrasena = editarContrasenaNueva.getText().toString();
                String newContrasenaConfirm = editarConfirmarContrasena.getText().toString();
                String actCorreo = editarCorreo.getText().toString();

                if (validarCorreoElectronico(actCorreo)) {
                    actUsuario.setCorreoElectronico(actCorreo);
                    if (!nombreUsuario.isEmpty()) {
                        actUsuario.setNombre(nombreUsuario);
                        if (actContrasena.equals(actUsuario.getContrasena())) {
                            if (newContrasena.isEmpty() && newContrasenaConfirm.isEmpty()) {
                                // Actualizar el usuario en la base de datos sin cambiar la contraseña
                                cad.actualizarUsuario(actUsuario);
                                return true;
                            } else if (newContrasena.equals(newContrasenaConfirm)) {
                                if (verificarContrasenaSegura(newContrasena)) {
                                    // Actualizar el usuario en la base de datos con la nueva contraseña y correo electrónico
                                    actUsuario.setContrasena(newContrasena);
                                    cad.actualizarUsuario(actUsuario);
                                    return true;
                                } else {
                                    errores = "La nueva contraseña no cumple con los criterios de seguridad";
                                }
                            } else {
                                errores = "Las nuevas contraseñas no coinciden";
                            }
                        } else {
                            errores = "La contraseña actual es incorrecta o está vacía";
                        }
                    } else {
                        errores = "El nombre de usuario no puede estar vacío";
                    }
                } else {
                    errores = "El correo electrónico no es válido";
                }

                return false;
            } catch (ExcepcionMyGameList | SQLException e) {
                e.printStackTrace();
                return false;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            View view = findViewById(android.R.id.content);
            if (success) {
                userSingleton.setUsuario(actUsuario);
                Snackbar.make(view, "El usuario se ha actualizado correctamente", Snackbar.LENGTH_LONG).show();
            } else {
                errores = "Error al actualizar el usuario "  + errores;
                if (errores != null && !errores.isEmpty()) {
                    Snackbar.make(view, errores, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "El correo o el nombre de usuario ya están en uso", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }



}