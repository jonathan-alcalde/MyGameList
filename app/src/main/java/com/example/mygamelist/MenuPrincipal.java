package com.example.mygamelist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;
import pojosmygamelist.Juego;
import pojosmygamelist.Usuario;

public class MenuPrincipal extends AppCompatActivity {
    private TextView usuarioLogeado;
    private GridView gridView;
    private ImageAdapter adapter;

    private ArrayList<Juego> juegos;
    private UserSingleton userSingleton;
    private ImageView buscar;
    private EditText textobuscar;
    private Usuario usuarioencontrado;
    private CADMyGameList cad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        try {
            cad = new CADMyGameList();
        } catch (ExcepcionMyGameList e) {
            throw new RuntimeException(e);
        }

        buscar = findViewById(R.id.buscarUsuario);
        buscar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nombreUsuario = textobuscar.getText().toString();
                if (!nombreUsuario.isEmpty()) {
                    ObtenerUsuarioTask hilo = new ObtenerUsuarioTask();
                    hilo.execute(nombreUsuario);
                } else {
                    Snackbar.make(v, "El nombre de usuario está vacío", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        textobuscar = findViewById(R.id.textbuscarusuario);

        gridView = findViewById(R.id.grid_view);

        ObtenerJuegosTask obtenerJuegosTask = new ObtenerJuegosTask();
        obtenerJuegosTask.execute();

        usuarioLogeado = findViewById(R.id.usuario_logeado);
        userSingleton = UserSingleton.getInstance(); // Obtener la instancia del UserSingleton
        usuarioLogeado.setText(userSingleton.getUsuario().getNombre());

        adapter = new ImageAdapter();
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (juegos != null && position < juegos.size()) {
                    Juego juego = juegos.get(position);
                    Intent intent = new Intent(MenuPrincipal.this, JuegoEnMenu.class);
                    intent.putExtra("juegoSeleccionado", juego);
                    startActivity(intent);
                }
            }
        });

        // Configuración del menú desplegable
        Button botonMenuPrincipal = findViewById(R.id.menuPrincipal);
        botonMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Snackbar.make(v, "Ya te encuentras en el menu principal", Snackbar.LENGTH_LONG).show();
            }
        });

        Button miLista = findViewById(R.id.miLista);
        miLista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ListaPersonal.class);
                startActivity(intent);
            }
        });

        Button editarUsuario = findViewById(R.id.datosUsuario);
        editarUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, EditarUsuario.class);
                startActivity(intent);
            }
        });

        Button cerrarSesion = findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, Login.class);
                startActivity(intent);
            }
        });
    }

    class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (juegos != null) {
                return juegos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MenuPrincipal.this).inflate(R.layout.grid_item, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.imageView);

            Juego juego = juegos.get(position);
            Picasso.get().load(juego.getImagen()).into(imageView);

            return convertView;
        }
    }

    private class ObtenerJuegosTask extends AsyncTask<Void, Void, ArrayList<Juego>> {
        @Override
        protected ArrayList<Juego> doInBackground(Void... voids) {
            ArrayList<Juego> juegos = new ArrayList<>();
            try {
                juegos = cad.getJuegosTodo();
            } catch (ExcepcionMyGameList | SQLException e) {
                e.printStackTrace();
            }
            return juegos;
        }

        @Override
        protected void onPostExecute(ArrayList<Juego> juegos) {
            MenuPrincipal.this.juegos = juegos;
            adapter.notifyDataSetChanged();
        }
    }

    private class ObtenerUsuarioTask extends AsyncTask<String, Void, Usuario> {
        private String errores;

        @Override
        protected Usuario doInBackground(String... params) {
            String nombreUsuario = textobuscar.getText().toString();
            try {
                Usuario usuarioEncontrado = cad.obtenerNombreUsuario(nombreUsuario);
                return usuarioEncontrado;
            } catch (ExcepcionMyGameList | SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Usuario usuarioEncontrado) {
            View view = findViewById(android.R.id.content);
            if (usuarioEncontrado != null) {
                // Acción exitosa: El usuario fue encontrado
                Intent intent = new Intent(MenuPrincipal.this, ListaAjena.class);
                intent.putExtra("otrousuario", usuarioEncontrado.getNombre());
                startActivity(intent);
            } else {
                // Acción fallida: El usuario no fue encontrado
                Snackbar.make(view, "Usuario no encontrado", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
