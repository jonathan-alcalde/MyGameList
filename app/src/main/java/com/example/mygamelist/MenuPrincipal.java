package com.example.mygamelist;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        gridView = findViewById(R.id.grid_view);

        ObtenerJuegosTask obtenerJuegosTask = new ObtenerJuegosTask();
        obtenerJuegosTask.execute();

        usuarioLogeado = findViewById(R.id.usuario_logeado);
        usuarioLogeado.setText(UserSingleton.usuario.getNombre());

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

//////////////////////////////////////////////CONFIGURACION DEL MENU DESPLEGABLE////////////////////////////////////////////////////

        //Boton en navegacion para menu principal
        Button BotonMenuPrincipal = findViewById(R.id.menuPrincipal);
        BotonMenuPrincipal.setOnClickListener(new View.OnClickListener() {

            //metodo para viajar al registro
            public void onClick(View v) {

                // Crear un objeto Intent para la actividad de destino
                Intent intent = new Intent(MenuPrincipal.this, MenuPrincipal.class);



                // Iniciar la actividad de destino
                startActivity(intent);
            }
        });

        //Boton en navegacion para lista personal
        Button miLista = findViewById(R.id.miLista);
        miLista.setOnClickListener(new View.OnClickListener() {

            //metodo para viajar al registro
            public void onClick(View v) {

                // Crear un objeto Intent para la actividad de destino
                Intent intent = new Intent(MenuPrincipal.this, ListaPersonal.class);

                // Iniciar la actividad de destino
                startActivity(intent);
            }
        });
        //////////////////////////////////////////////FIN DE CONFIGURACION DEL MENU DESPLEGABLE////////////////////////////////////////////////////


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

            Juego juego = juegos.get(position); // Obtener el juego correspondiente
            // Aquí debes establecer la imagen del juego en el ImageView utilizando Picasso o cualquier otra biblioteca de carga de imágenes
            Picasso.get().load(juego.getImagen()).into(imageView);

            return convertView;
        }

    }

    private class ObtenerJuegosTask extends AsyncTask<Void, Void, ArrayList<Juego>> {

        @Override
        protected ArrayList<Juego> doInBackground(Void... voids) {
            ArrayList<Juego> juegos = new ArrayList<>();
            try {
                CADMyGameList cad = new CADMyGameList();
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
