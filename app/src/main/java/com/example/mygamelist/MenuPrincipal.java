package com.example.mygamelist;


import android.content.Context;
import android.content.Intent;
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

import org.w3c.dom.Text;

import pojosmygamelist.Usuario;


public class MenuPrincipal extends AppCompatActivity {
    private TextView usuarioLogeado;
    private GridView gridView;
    private ImageAdapter adapter;
    private int[] images = {R.drawable.j1, R.drawable.j2, R.drawable.j3, R.drawable.j4, R.drawable.j5, R.drawable.j6, R.drawable.j7, R.drawable.j8, R.drawable.j9, R.drawable.j10, R.drawable.j11, R.drawable.j12, R.drawable.j13, R.drawable.j14, R.drawable.j15, R.drawable.j16, R.drawable.j17, R.drawable.j18, R.drawable.j19, R.drawable.j20, R.drawable.j21};

    //Juego juego = new Juego("Heltaker","ElSangu","Helltaker es un juego de aventuras y rompecabezas indie gratuito con elementos de simulación de citas diseñado por el desarrollador polaco Łukasz Piskorz,también conocido como Vanripper.","2D","PC",R.drawable.j1);
    //Juego juego1 = new Juego("Hollow Knight","Team Cherry","¡Forja tu propio camino en Hollow Knight! Una aventura épica a través de un vasto reino de insectos y héroes que se encuentra en ruinas. Explora cavernas tortuosas, combate contra criaturas corrompidas y entabla amistad con extraños insectos.","2D","PC",R.drawable.j2);
    //Juego juego2 = new Juego("Horizon Zero Dawn","Guerrilla","Horizon Zero Dawn es un videojuego de acción, aventura y de mundo abierto desarrollado por Guerrilla Games y distribuido por Sony Interactive Entertainment para PlayStation 4.","Accion","PC",R.drawable.j3);

    public Juego [] juegos ={new Juego("Heltaker","ElSangu","Helltaker es un juego de aventuras y rompecabezas indie gratuito con elementos de simulación de citas diseñado por el desarrollador polaco Łukasz Piskorz,también conocido como Vanripper.","2D","PC",R.drawable.j1),
                            new Juego("Hollow Knight","Team Cherry","¡Forja tu propio camino en Hollow Knight! Una aventura épica a través de un vasto reino de insectos y héroes que se encuentra en ruinas. Explora cavernas tortuosas, combate contra criaturas corrompidas y entabla amistad con extraños insectos.","2D","PC",R.drawable.j2),
                            new Juego("Horizon Zero Dawn","Guerrilla","Horizon Zero Dawn es un videojuego de acción, aventura y de mundo abierto desarrollado por Guerrilla Games y distribuido por Sony Interactive Entertainment para PlayStation 4.","Accion","PC",R.drawable.j3),
                            new Juego("Hot por for one", "Rachel Li, Qin Yin", "You are alone on a cold, Christmas night, stuck at home, in a foreign country. You get through it by cooking a hot dish intended for six people.", "Simulador", "PC", R.drawable.j4),
                            new Juego("Hotline Miami", "Dennaton Games", "Hotline Miami es un juego de acción de alto octanaje que rebosa brutalidad es estado puro, violentos tiroteos y demoledores combates cuerpo a cuerpo.", "Accion", "PC", R.drawable.j5),
                            new Juego("Hotline Miami 2", "Dennaton Games", "Hotline Miami 2: Wrong Number es la brutal conclusión de la saga Hotline Miami que se desarrolla en plena escalada de violencia y venganza por la sangre derramada en el juego original.", "Accion", "PC", R.drawable.j6),
                            new Juego("Katana Zero", "Askiisoft", "Katana ZERO es un juego de acción y plataformas neo-noir rebosante de personalidad, acción vertiginosa y combates de muerte instantánea. Usa tu espada, corre y manipula el tiempo para desvelar tu pasado en un despliegue acrobático brutalmente estético.", "Accion", "PC", R.drawable.j7),
                            new Juego("Keo", "Redcatpig Studio", "KEO is a team-based online multiplayer vehicle combat game set in a sci-fi post-apocalyptic world. Build your loadout to suit your playstyle and balance your team to dominate the battlefield using futuristic remote controlled vehicles!", "Carreras", "PC", R.drawable.j8),
                            new Juego("Kingdom noseque", "Noio, Licorice", "Kingdom is a 2D sidescrolling strategy/resource management hybrid with a minimalist feel wrapped in a beautiful, modern pixel art aesthetic.", "Gestion de recursos", "PC", R.drawable.j9),
                            new Juego("La Mulana", "NIGORO", "LA-MULANA is an “Archaeological Ruin Exploration Action Game,” bringing the classic appeal of adventure with the punishing difficulty of retro-inspired gaming. Search inside ancient ruins, seeking out the “Secret Treasure of Life” ", "Puzzles", "PC", R.drawable.j10),
                            new Juego("Lara croft Guardiana de la luz", "Crystal Dynamics", "Toma el control de Lara y su nuevo compañero Totec, miembro de una tribu Maya, y descubre un antiguo artefacto conocido como el Espejo de Humo en esta nueva aventura de acción.", "Accion", "PC", R.drawable.j11),
                            new Juego("Lara Croft El Templo de Osiris", "Crystal Dynamics", "Lara Croft and the Temple of Osiris es la continuación del aclamado Lara Croft and the Guardian of Light y la primera experiencia cooperativa para cuatro jugadores de Lara Croft.", "Accion", "PC", R.drawable.j12)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        //Usuario u1 = (Usuario) getIntent().getSerializableExtra("usuario");
        //System.out.println(u1.getId());
        gridView = findViewById(R.id.grid_view);

        usuarioLogeado = findViewById(R.id.usuario_logeado);
        usuarioLogeado.setText(UserSingleton.usuario.getNombre());

        adapter = new ImageAdapter();
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Acción a realizar cuando se hace clic en una imagen
                // Aquí puedes redirigir a una actividad en particular

                Intent intent = new Intent(MenuPrincipal.this, JuegoEnMenu.class);
                // Agrega cualquier dato adicional que necesites enviar a la actividad destino
                intent.putExtra("juegoSeleccionado", juegos[position]);

                startActivity(intent);
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





    }

    public void funcion(){

    }


    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return juegos.length;
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

            imageView.setImageResource(juegos[position].getImage());

            return convertView;
        }
    }
}