package com.pfgunir.appquatic.Ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.pfgunir.appquatic.Core.AppQuatic;
import com.pfgunir.appquatic.Core.Spot;
import com.pfgunir.appquatic.Core.Usuario;
import com.pfgunir.appquatic.R;
import com.squareup.picasso.Picasso;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListaSpots extends AppCompatActivity implements
        OnQueryTextListener {

    private ArrayList <Spot> ListaDeSpots;
     private MiAdaptadorDeListas adaptadorspots;
     Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_spots);
        ListaDeSpots = ((AppQuatic) this.getApplication()).getListaDeSpots();
        rellenaLista(ListaDeSpots);
        recuperaUsuario();

        getSupportActionBar().setTitle(getSupportActionBar().getTitle());
        getSupportActionBar().setSubtitle("Lista de Spots");




    }
     private void rellenaLista(ArrayList<Spot> listaDeSpots) {
         ArrayList<Spot> itemsArrayList = (ArrayList<Spot>) listaDeSpots;

         adaptadorspots = new MiAdaptadorDeListas(this, itemsArrayList);
         ListView itemsListView = (ListView) findViewById(R.id.list);
         itemsListView.setAdapter(adaptadorspots);
         itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Spot listItem = (Spot) itemsListView.getItemAtPosition(position);
                 Intent i = new Intent(ListaSpots.this, DetalleSpot.class);

                 i.putExtra("spot_id", listItem.getIdspot());
                 finish();
                 startActivity(i);
             }
         });



     }

    private void recuperaUsuario() {

        usuario = ((AppQuatic) getApplication()).getUsuario();

    }

     @Override
     public boolean onQueryTextSubmit(String query) {
         adaptadorspots.getFilter().filter(query.toString());
        return false;
     }

     @Override
     public boolean onQueryTextChange(String newText) {
         adaptadorspots.getFilter().filter(newText.toString());
         return false;
     }

     public class MiAdaptadorDeListas extends BaseAdapter implements Filterable {
         private ArrayList<Spot> spots ;
         ArrayList<Spot> mOriginalValues;

         private Context context; //context

         //public constructor
         public MiAdaptadorDeListas(Context context, ArrayList<Spot> items) {
             super();
             this.context = context;
             this.spots = items;

         }

         @Override
         public int getCount() {
             return spots.size();
         }

         @Override
         public Object getItem(int position) {


             return spots.get(position);
         }

         @Override
         public long getItemId(int position) {
             return position;
         }

         @Override
         public View getView(int position, View convertView, ViewGroup parent) {
             // inflate the layout for each list row
             if (convertView == null) {
                 convertView = LayoutInflater.from(context).
                         inflate(R.layout.row_lista_spot, parent, false);
             }

             // get current item to be displayed
             Spot currentItem = (Spot) getItem(position);

             // get the TextView for item name and item description
             TextView textViewItemName = (TextView)
                     convertView.findViewById(R.id.row_lista_spot_text_view_nombre_spot);
             ;

             //sets the text for item name and item description from the current item object
             textViewItemName.setText(currentItem.getNombreSpot());
             textViewItemName.setBackgroundColor(Color.CYAN);


             ImageView imgWeatherIcon = (ImageView) convertView.findViewById(R.id.row_lista_spot_image_view_icono_open);


             Picasso.get().load("https://openweathermap.org/img/w/" + currentItem.getDatosOpenWeather().getIcon() + ".png").into(imgWeatherIcon);


             TextView textViewtiempo = (TextView)
                     convertView.findViewById(R.id.row_lista_spot_text_view_tiempo);
             textViewtiempo.setText("\t" + "Clima:" + "\t" + currentItem.getDatosOpenWeather().getDescription() + " " + "Temp " + currentItem.getDatosOpenWeather().getTemp() + "°");

             TextView textViewviento = (TextView)
                     convertView.findViewById(R.id.row_lista_spot_text_view_viento);
             ;

          textViewviento.setText("\t" + "Viento:" + "\t" + currentItem.getDatosOpenWeather().getDireccionViento() + " " + currentItem.getDatosOpenWeather().getWind_deg() + "° Fuerza " + currentItem.getDatosOpenWeather().getWind_speed() + " m/s");
             //Log.i(getClass().getSimpleName(), currentItem.getNombreSpot() + "-->" + currentItem.getDatosMeteoAemet().getViento());

             String ruta_imagen = "";
             int id_imagen;
             ImageView imgvientoIcon = convertView.findViewById(R.id.row_lista_spot_image_view_icono_viento);
            ruta_imagen = "icono_viento_" +String.valueOf( currentItem.getDatosOpenWeather().getFuerzaVientoIcono());

             id_imagen = getResources().getIdentifier(ruta_imagen, "drawable", getPackageName());
            Picasso.get().load(id_imagen).into(imgvientoIcon);


             ImageView imgDireccionVientoIcon = convertView.findViewById(R.id.row_lista_spot_image_view_icono_direccion_viento);
           ruta_imagen = "icono_" +String.valueOf(currentItem.getDatosOpenWeather().getDireccionViento()).toLowerCase() + "_wind";
             Log.i(getClass().getSimpleName(), ruta_imagen);
             id_imagen = getResources().getIdentifier(ruta_imagen, "drawable", getPackageName());
           Picasso.get().load(id_imagen).into(imgDireccionVientoIcon);


             TextView textViewoleaje = (TextView)
                     convertView.findViewById(R.id.row_lista_spot_text_view_oleaje);
             ;
             textViewoleaje.setText("\t" + "Oleje:" + "\t" + currentItem.getDatosAemet().getOleaje());

             // Log.i(getClass().getSimpleName(), "Oleaje " + currentItem.getDatosMeteoAemet().getOleaje());


             ImageView imgoleajeIcon = convertView.findViewById(R.id.row_lista_spot_image_view_icono_oleaje);
             ruta_imagen = "icono_olas_" + currentItem.getDatosAemet().getOleajeIcono();
             id_imagen = getResources().getIdentifier(ruta_imagen, "drawable", getPackageName());
             Picasso.get().load(id_imagen).into(imgoleajeIcon);

             //colorea_iconos_deportes(convertView ,currentItem);

             // returns the view for the current row
             rellena_material_deportes(convertView, currentItem);
             colorea_iconos_deportes(convertView, currentItem);


             return convertView;

         }

         private void colorea_iconos_deportes(View convertView, Spot spot) {

             ImageView img_icono_windsurf = convertView.findViewById(R.id.row_lista_spot_image_view_windsurf);
             TextView textinfowindsurf = (TextView)
                     convertView.findViewById(R.id.row_lista_spot_text_view_infowin_windsurf);





             switch (spot.getEstadoWindSurf()) {
                 case 0:
                     break;
                 case 1:
                     img_icono_windsurf.setBackgroundColor(Color.RED);
                     textinfowindsurf.setBackgroundColor(Color.RED);
                     textinfowindsurf.setText("NO practicable");
                     break;
                 case 2:
                     img_icono_windsurf.setBackgroundColor(Color.YELLOW);
                     textinfowindsurf.setBackgroundColor(Color.YELLOW);
                     break;
                 case 3:
                     img_icono_windsurf.setBackgroundColor(Color.GREEN);
                     textinfowindsurf.setBackgroundColor(Color.GREEN);
                     break;
                 default:
                     img_icono_windsurf.setBackgroundColor(Color.BLACK);
                     textinfowindsurf.setBackgroundColor(Color.BLACK);
                     textinfowindsurf.setText("NO practicable");
                     break;

             }



             ImageView img_icono_Kitesurf = convertView.findViewById(R.id.row_lista_spot_image_view_kitesurf);
             TextView textinfokitsurf = (TextView)
                     convertView.findViewById(R.id.row_lista_spot_text_view_info_kitesurf);




             switch (spot.getEstadoKiteSurf()) {
                 case 0:
                     break;
                 case 1:
                     img_icono_Kitesurf.setBackgroundColor(Color.RED);
                     textinfokitsurf.setBackgroundColor(Color.RED);
                     textinfokitsurf.setText("NO practicable");
                     break;
                 case 2:
                     img_icono_Kitesurf.setBackgroundColor(Color.YELLOW);
                     textinfokitsurf.setBackgroundColor(Color.YELLOW);
                     break;
                 case 3:
                     img_icono_Kitesurf.setBackgroundColor(Color.GREEN);
                     textinfokitsurf.setBackgroundColor(Color.GREEN);
                     break;
                 default:
                     img_icono_Kitesurf.setBackgroundColor(Color.BLACK);
                     textinfokitsurf.setBackgroundColor(Color.BLACK);
                     textinfokitsurf.setText("NO practicable");
                     break;


             }


             ImageView img_icono_Surf = convertView.findViewById(R.id.row_lista_spot_image_view_surf);
             TextView textinfoSurf = (TextView)
                     convertView.findViewById(R.id.row_lista_spot_text_view_info_surf);
             


             switch (spot.getEstadoSurf()) {
                 case 0:
                     break;
                 case 1:
                     img_icono_Surf.setBackgroundColor(Color.RED);
                     textinfoSurf.setBackgroundColor(Color.RED);
                     textinfoSurf.setText("NO practicable");

                     break;
                 case 2:
                     img_icono_Surf.setBackgroundColor(Color.YELLOW);
                     textinfoSurf.setBackgroundColor(Color.YELLOW);
                     break;
                 case 3:
                     img_icono_Surf.setBackgroundColor(Color.GREEN);
                     textinfoSurf.setBackgroundColor(Color.GREEN);
                     break;
                 default:
                     img_icono_Surf.setBackgroundColor(Color.BLACK);
                     textinfoSurf.setBackgroundColor(Color.BLACK);
                     textinfoSurf.setText("NO practicable");;
                     break;


             }


         }

         @Override
         public Filter getFilter() {
             Filter filter = new Filter() {

                 @SuppressWarnings("unchecked")
                 @Override
                 protected void publishResults(CharSequence constraint,
                                               FilterResults results) {

                     spots = (ArrayList<Spot>) results.values;
                     notifyDataSetChanged();
                 }

                 @Override
                 protected FilterResults performFiltering(CharSequence constraint) {
                     FilterResults results = new FilterResults();
                     ArrayList<Spot> FilteredArrList = new ArrayList<Spot>();

                     if (mOriginalValues == null) {
                         mOriginalValues = spots;
                     }

                     if (constraint == null || constraint.length() == 0) {

                         results.count = mOriginalValues.size();
                         results.values = mOriginalValues;
                     } else {
                         constraint = constraint.toString().toLowerCase();
                         for (int i = 0; i < mOriginalValues.size(); i++) {
                             String data = mOriginalValues.get(i)
                                     .getNombreSpot();
                             if (data.toLowerCase().contains(
                                     constraint.toString())) {
                                 FilteredArrList.add(mOriginalValues.get(i));
                             }
                         }

                         results.count = FilteredArrList.size();
                         results.values = FilteredArrList;
                     }
                     return results;
                 }
             };
             return filter;
         }
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.lista_spots_menu, menu);

         return true;

     }

     @Override
     public boolean onPrepareOptionsMenu(Menu menu) {

         SearchView mSearchView;

         MenuItem searchItem = menu.findItem(R.id.action_lista_buscar);
         mSearchView = (SearchView) searchItem.getActionView();
         mSearchView.setQueryHint(getString(R.string.lista_buscar_menu));
         mSearchView.setOnQueryTextListener(this);

         if(usuario.getModofavoritos()==1){

             menu.findItem(R.id.action_lista_favorito_mode).setIcon(android.R.drawable.btn_star_big_on);

         }
         else {
             menu.findItem(R.id.action_lista_favorito_mode).setIcon(android.R.drawable.btn_star_big_off);
         }
         return super.onPrepareOptionsMenu(menu);

     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        Usuario usuario = ((AppQuatic) this.getApplication()).getUsuario();
         switch (item.getItemId()) {
             case R.id.action_lista_favorito_mode:
                 //modoFavoritos(item);
                 Log.i(getClass().getSimpleName(), "Favorivos pulsado !!! !!!!");
                 if(usuario.getModofavoritos()==0){
                     usuario.setModofavoritos(1);
                     ((AppQuatic) this.getApplication()).getUsuario().setModofavoritos(1);
                     ListaDeSpots = ((AppQuatic) this.getApplication()).getListaDeSpots();
                     rellenaLista(ListaDeSpots);
                     item.setIcon(android.R.drawable.btn_star_big_on);

                     //finish();
                     //startActivity(getIntent());
                 }
                 else{
                     //rellenaLista(ListaDeSpots);
                     item.setIcon(android.R.drawable.btn_star_big_off);
                     usuario.setModofavoritos(0);
                     ((AppQuatic) this.getApplication()).getUsuario().setModofavoritos(0);
                     ListaDeSpots = ((AppQuatic) this.getApplication()).getListaDeSpots();
                     rellenaLista(ListaDeSpots);
                    // finish();
                     //startActivity(getIntent());
                 }
                 //finish();
                 //startActivity(getIntent());



                 break;
             case R.id.action_lista_actualizar_lista:
                 try {
                     actualizaSpots(ListaDeSpots);
                 } catch (ExecutionException e) {
                     e.printStackTrace();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }



                  finish();
                  startActivity(getIntent());

                 break;

             default:
                 return super.onOptionsItemSelected(item);
         }

         return true;
     }

    private void actualizaSpots(ArrayList<Spot> listaDeSpots_a_actualizar) throws ExecutionException, InterruptedException {

        DescargaConBarraUi descarga = new DescargaConBarraUi(this, this, this.getString(R.string.barra_progreso_descargando_lecturas), ListaDeSpots);
        Object result = descarga.execute().get();

    }




    private void rellena_material_deportes(View convertView, Spot spot) {


        TextView textinfowindsurf = (TextView)
                convertView.findViewById(R.id.row_lista_spot_text_view_infowin_windsurf);

        TextView textinfokitsurf = (TextView)
                convertView.findViewById(R.id.row_lista_spot_text_view_info_kitesurf);

        TextView textinfoSurf = (TextView)
                convertView.findViewById(R.id.row_lista_spot_text_view_info_surf);


        int[] MaterialWindSurf = spot.getMaterialWindSurf(usuario);
        int[] MaterialKiteSurf = spot.getMaterialKiteSurf(usuario);
        int[] MaterialSurf = spot.getMaterialSurf(usuario);


        textinfowindsurf.setText("Tabla: " + MaterialWindSurf[0] + System.getProperty("line.separator")
                + "Vela: " + MaterialWindSurf[1]);

        textinfokitsurf.setText("Tabla: " + MaterialKiteSurf[0] + System.getProperty("line.separator")
                + "Cometa: " + MaterialKiteSurf[1]);

        textinfoSurf.setText("Tabla: " + MaterialSurf[0] + "/" + MaterialSurf[1]);


    }



 }