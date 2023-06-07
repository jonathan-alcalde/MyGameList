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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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

    CADMyGameList cad = new CADMyGameList();

    public MenuPrincipal() throws ExcepcionMyGameList {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

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
                Intent intent = new Intent(MenuPrincipal.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });

        Button miLista = findViewById(R.id.miLista);
        miLista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuPrincipal.this, ListaPersonal.class);
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
}
