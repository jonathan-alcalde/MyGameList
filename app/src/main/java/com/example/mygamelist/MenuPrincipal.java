package com.example.mygamelist;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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