package com.example.mygamelist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;

import pojosmygamelist.CADMyGameList;
import pojosmygamelist.ExcepcionMyGameList;
import pojosmygamelist.Juego;
import pojosmygamelist.Lista;


public class ListaPersonal extends AppCompatActivity {

    private GridView gridView;
    private TextView usuarioLogeado;
    private ImageAdapter adapter;

    private ArrayList<Lista> listapersonal;
    private UserSingleton userSingleton;
    UserSingleton singleton = UserSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleton = UserSingleton.getInstance();
        setContentView(R.layout.activity_lista_personal);
        gridView = findViewById(R.id.grid_view);

        ObtenerLista obtenerlista = new ListaPersonal.ObtenerLista();
        obtenerlista.execute();

        adapter = new ImageAdapter();
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listapersonal != null && position < listapersonal.size()) {
                    Lista lista = listapersonal.get(position);
                    Intent intent = new Intent(ListaPersonal.this, JuegoEnListaPersonal.class);
                    intent.putExtra("juegoSeleccionado", lista);
                    startActivity(intent);
                }
            }
        });

        // Configuración del menú desplegable
        usuarioLogeado = findViewById(R.id.usuario_logeado);
        userSingleton = UserSingleton.getInstance(); // Obtener la instancia del UserSingleton
        usuarioLogeado.setText(userSingleton.getUsuario().getNombre());
        Button botonMenuPrincipal = findViewById(R.id.menuPrincipal);
        botonMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListaPersonal.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });

        Button miLista = findViewById(R.id.miLista);
        miLista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Snackbar.make(v, "Ya te encuentras en tu lista", Snackbar.LENGTH_LONG).show();
            }
        });

        Button editarUsuario = findViewById(R.id.datosUsuario);
        editarUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListaPersonal.this, EditarUsuario.class);
                startActivity(intent);
            }
        });

        Button cerrarSesion = findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListaPersonal.this, Login.class);
                startActivity(intent);
            }
        });

    }



    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (listapersonal != null) {
                return listapersonal.size();
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
                convertView = LayoutInflater.from(ListaPersonal.this).inflate(R.layout.grid_item, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.imageView);

            Lista lista = listapersonal.get(position); // Obtener el juego correspondiente
            // Aquí debes establecer la imagen del juego en el ImageView utilizando Picasso o cualquier otra biblioteca de carga de imágenes
            Picasso.get().load(lista.getImagen()).into(imageView);

            return convertView;
        }

    }
    private class ObtenerLista extends AsyncTask<Void, Void, ArrayList<Lista>> {

        @Override
        protected ArrayList<Lista> doInBackground(Void... voids) {
            ArrayList<Lista> listapersonal = new ArrayList<>();
            try {
                CADMyGameList cad = new CADMyGameList();
                System.out.println("nombre " + singleton.getUsuario().getNombre());
                listapersonal = cad.obtenerLista(singleton.getUsuario().getId());

            } catch (ExcepcionMyGameList | SQLException e) {
                e.printStackTrace();
            }

            return listapersonal;
        }

        @Override
        protected void onPostExecute(ArrayList<Lista> listapersonal) {
            ListaPersonal.this.listapersonal = listapersonal;
            adapter.notifyDataSetChanged();
        }
    }

}