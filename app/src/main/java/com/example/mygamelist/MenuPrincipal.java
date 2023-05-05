package com.example.mygamelist;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;




public class MenuPrincipal extends AppCompatActivity {

    private Integer[] imageResources = {
            R.drawable.j1,
            R.drawable.j2,
            R.drawable.j3,
            R.drawable.j4,
            R.drawable.j5,
            R.drawable.j6,
            R.drawable.j7,
            R.drawable.j8,
            R.drawable.j9,
            R.drawable.j10,
            R.drawable.j11,
            R.drawable.j12,
            R.drawable.j13,
            R.drawable.j14,
            R.drawable.j15,
            R.drawable.j16,
            R.drawable.j17,
            R.drawable.j18,
            R.drawable.j19,
            R.drawable.j20,
            R.drawable.j21
    };

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        gridView = findViewById(R.id.grid_view);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
                this,
                android.R.layout.simple_list_item_1,
                imageResources
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(imageResources[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        350
                ));
                return imageView;
            }
        };
        gridView.setAdapter(adapter);
    }
}