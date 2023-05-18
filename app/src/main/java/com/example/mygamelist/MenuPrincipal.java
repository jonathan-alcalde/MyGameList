package com.example.mygamelist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;




public class MenuPrincipal extends AppCompatActivity {

    private GridView gridView;
    private ImageAdapter adapter;
    private int[] images = {R.drawable.j1, R.drawable.j2, R.drawable.j3, R.drawable.j4, R.drawable.j5, R.drawable.j6, R.drawable.j7, R.drawable.j8, R.drawable.j9, R.drawable.j10, R.drawable.j11, R.drawable.j12, R.drawable.j13, R.drawable.j14, R.drawable.j15, R.drawable.j16, R.drawable.j17, R.drawable.j18, R.drawable.j19, R.drawable.j20, R.drawable.j21};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        gridView = findViewById(R.id.grid_view);

        adapter = new ImageAdapter();
        gridView.setAdapter(adapter);

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



    }


    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
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

            imageView.setImageResource(images[position]);

            return convertView;
        }
    }
}