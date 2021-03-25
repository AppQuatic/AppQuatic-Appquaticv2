package com.pfgunir.appquatic.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pfgunir.appquatic.Core.AppQuatic;
import com.pfgunir.appquatic.Core.Constantes;
import com.pfgunir.appquatic.Core.Spot;
import com.pfgunir.appquatic.Core.Usuario;
import com.pfgunir.appquatic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ArrayList<Spot> ListaDeSpots;
    private LatLng posicion_camara = Constantes.PosicionUnir;
    private int zoom_camara = 5;
    private String subtitulo = "Mapa de spots";
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ListaDeSpots = ((AppQuatic) this.getApplication()).getListaDeSpots();
        recogerExtras();
        recuperaUsuario();
        getSupportActionBar().setTitle(getSupportActionBar().getTitle());
        getSupportActionBar().setSubtitle(subtitulo);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        rellenamapa(mMap);


    }


    /**
     * Sitúa la cámara sobre un punto del mapa.
     *
     * @param coordenadasCamara coordenadas a situar la cámara.
     * @param zoom              Zoom deseado
     */
    private void situaCamara(LatLng coordenadasCamara, int zoom) {
        if (coordenadasCamara != Constantes.PosicionUnir) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadasCamara,
                    zoom));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constantes.PosicionUnir,
                    zoom));
        }
    }


    public boolean onMarkerClick(Marker marker) {
// Retrieve the data from the marker.

        //Log.i(getClass().getSimpleName(), marker.getTitle());
        Spot spot = (Spot) marker.getTag();
        //Log.i(getClass().getSimpleName(), spot.getDatosMeteoAemet().getOleaje_manyana());
        //this.situaCamara(marker.getPosition(), 5);
        // Check if a click count was set, then display the click count.


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        //Log.i(getClass().getSimpleName(), marker.getTitle());
        lanzarActivitySpot((Spot) marker.getTag());

    }

    private void lanzarActivitySpot(Spot spot) {


        Intent i = new Intent(MapsActivity.this, DetalleSpot.class);

        i.putExtra("spot_id", spot.getIdspot());
        //startActivity(i);
        finish();
        startActivity(i);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    public class MapsInfoWindow implements GoogleMap.InfoWindowAdapter {

        @Override
        public View getInfoWindow(Marker marker) {

            Spot spot = (Spot) marker.getTag();
            //Log.i(getClass().getSimpleName(), spot.getNombre() + " Elaborado " + spot.getDatosMeteoAemet().getElaborado());


            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {

            //String color_fondo = Color.YELLOW;
            View myContentsView = getLayoutInflater().inflate(
                    R.layout.activity_maps_info_window, null);
            // myContentsView.setBackgroundColor(Color.parseColor(color_fondo));


            Spot spot = (Spot) marker.getTag();

            ImageView imgSurfIcon = myContentsView.findViewById(R.id.info_window_imageView_icono_surf);
            //Picasso.get().load(R.drawable.icono_windsurf).into(imgSurfIcon);
            //imgSurfIcon.setBackgroundColor(Color.GREEN);

            ImageView imgWindSurfIcon = myContentsView.findViewById(R.id.info_window_imageView_icono_wind_surf);
            // imgWindSurfIcon.setBackgroundColor(Color.RED);

            ImageView imgKiteSurfIcon = myContentsView.findViewById(R.id.info_window_imageView_icono_kite_surf);
            //imgKiteSurfIcon.setBackgroundColor(Color.YELLOW);


            TextView txtnombre = (TextView) myContentsView.findViewById(R.id.detalle_spot_textView_nombre_spot);
            txtnombre.setText(spot.getNombreSpot());
            txtnombre.setBackgroundColor(Color.CYAN);


            ImageView imgWeatherIcon = (ImageView) myContentsView.findViewById(R.id.detalle_spot_imageView_icono_open);
            Picasso.get().load("https://openweathermap.org/img/w/" + spot.getDatosOpenWeather().getIcon() + ".png").into(imgWeatherIcon);
            TextView infoclima = (TextView) myContentsView.findViewById(R.id.info_window_textView_Info_clima);
            infoclima.setText("Clima:" + "\t" + "\t" + "Clima:" + "\t" + spot.getDatosOpenWeather().getDescription() + " " + "Temp " + spot.getDatosOpenWeather().getTemp() + "°");

            TextView infoviento = (TextView) myContentsView.findViewById(R.id.info_window_textView_Info_viento);
            infoviento.setText("\t" + "Viento:" + "\t" + spot.getDatosOpenWeather().getDireccionViento() + " " + spot.getDatosOpenWeather().getWind_deg() + "° Fuerza " + spot.getDatosOpenWeather().getWind_speed() + " m/s");

            String ruta_imagen = "";
            int id_imagen;
            ImageView imgvientoIcon = myContentsView.findViewById(R.id.info_window_imageView_icono_viento);
            ruta_imagen = "icono_viento_" + spot.getDatosOpenWeather().getFuerzaVientoIcono();
            id_imagen = getResources().getIdentifier(ruta_imagen, "drawable", getPackageName());
            Picasso.get().load(id_imagen).into(imgvientoIcon);

            ImageView imgDireccionVientoIcon = myContentsView.findViewById(R.id.info_window_imageView_icono_direccion_viento);
            ruta_imagen = "icono_" + spot.getDatosOpenWeather().getDireccionViento().toLowerCase() + "_wind";
            id_imagen = getResources().getIdentifier(ruta_imagen, "drawable", getPackageName());
            Picasso.get().load(id_imagen).into(imgDireccionVientoIcon);


            TextView infomar = (TextView) myContentsView.findViewById(R.id.info_window_textView_Info_mar);
            infomar.setText("\t" + "Oleje:" + "\t" + spot.getDatosAemet().getOleaje());


            ImageView imgoleajeIcon = myContentsView.findViewById(R.id.info_window_imageView_icono_mar);
            ruta_imagen = "icono_olas_" + spot.getDatosAemet().getOleajeIcono();
            id_imagen = getResources().getIdentifier(ruta_imagen, "drawable", getPackageName());
            Picasso.get().load(id_imagen).into(imgoleajeIcon);

            rellena_material_deportes(myContentsView, spot);
            colorea_iconos_deportes(myContentsView, spot);


            //TextView txtpredicion1 = (TextView) myContentsView.findViewById(R.id.TxtViewPredicion1);
            //txtnombre.setText(spot.getDatosMeteoAemet().getEstadoCielo_manyana());


            return myContentsView;
        }

    }

    public void recogerExtras() {
//Aquí recogemos y tratamos los parámetros
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String spot_id = extras.getString("spot_id");

            ListaDeSpots = ((AppQuatic) this.getApplication()).getListaDeSpots();

            for (int i = 0; i < ListaDeSpots.size(); i++) {

                if (ListaDeSpots.get(i).getIdspot().equals(spot_id)) {

                    posicion_camara = new LatLng(ListaDeSpots.get(i).getLat(), ListaDeSpots.get(i).getLon());
                    zoom_camara = 8;
                    subtitulo = subtitulo + " " + ListaDeSpots.get(i).getNombreSpot();


                }
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mapa_spots_menu, menu);

        return true;

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        if (usuario.getModofavoritos() == 1) {

            menu.findItem(R.id.action_map_favorito_mode).setIcon(android.R.drawable.btn_star_big_on);

        } else {
            menu.findItem(R.id.action_map_favorito_mode).setIcon(android.R.drawable.btn_star_big_off);
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
                if (usuario.getModofavoritos() == 1) {
                    //rellenaLista(ListaDeSpotsFavoritos);
                    item.setIcon(android.R.drawable.btn_star_big_on);
                    //usuario.setModofavoritos(true);
                    //finish();
                    //startActivity(getIntent());
                } else {
                    //rellenaLista(ListaDeSpots);
                    item.setIcon(android.R.drawable.btn_star_big_off);
                    //usuario.setModofavoritos(false);
                    //finish();
                    //startActivity(getIntent());
                }
                //finish();
                //startActivity(getIntent());


                break;
            case R.id.action_map_actualizar_lista:
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


    private void recuperaUsuario() {

        usuario = ((AppQuatic) getApplication()).getUsuario();

    }

    private void rellenamapa(GoogleMap mMap) {

        situaCamara(posicion_camara, zoom_camara);
        for (int i = 0; i < ListaDeSpots.size(); i++) {

            //for (int i = 0; i < 1; i++) {
            Log.i(getClass().getSimpleName(), ListaDeSpots.get(i).getNombreSpot());
            LatLng lt = new LatLng(ListaDeSpots.get(i).getLat(), ListaDeSpots.get(i).getLon());

            Marker markerSpot = mMap.addMarker(new MarkerOptions()
                    .position(lt)
                    .title(ListaDeSpots.get(i).getNombreSpot())
                    .snippet(
                            "hola"// nuevainfospot
                    )
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    ));

            markerSpot.setTag(ListaDeSpots.get(i));
            mMap.setInfoWindowAdapter(new MapsInfoWindow());
        }
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

    }

    private void actualizaSpots(ArrayList<Spot> listaDeSpots_a_actualizar) throws ExecutionException, InterruptedException {

        DescargaConBarraUi descarga = new DescargaConBarraUi(this, this, this.getString(R.string.barra_progreso_descargando_lecturas), ListaDeSpots);
        Object result = descarga.execute().get();

    }

    private void colorea_iconos_deportes(View convertView, Spot spot) {

        ImageView img_icono_windsurf = convertView.findViewById(R.id.info_window_imageView_icono_wind_surf);
        TextView textinfowindsurf = (TextView)
                convertView.findViewById(R.id.info_window_textView_windsurf);



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


        ImageView img_icono_Kitesurf = convertView.findViewById(R.id.info_window_imageView_icono_kite_surf);
        TextView textinfokitsurf = (TextView)
                convertView.findViewById(R.id.info_window_textView_kitesurf);



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


        ImageView img_icono_Surf = convertView.findViewById(R.id.info_window_imageView_icono_surf);
        TextView textinfoSurf = (TextView)
                convertView.findViewById(R.id.info_window_textView_surf);
        


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
                textinfoSurf.setText("NO practicable");
                ;
                break;


        }


    }

    private void rellena_material_deportes(View convertView, Spot spot) {


        TextView textinfowindsurf = (TextView)
                convertView.findViewById(R.id.info_window_textView_windsurf);

        TextView textinfokitsurf = (TextView)
                convertView.findViewById(R.id.info_window_textView_kitesurf);

        TextView textinfoSurf = (TextView)
                convertView.findViewById(R.id.info_window_textView_surf);


        int[] MaterialWindSurf = spot.getMaterialWindSurf(usuario);
        int[] MaterialKiteSurf = spot.getMaterialKiteSurf(usuario);
        int[] MaterialSurf = spot.getMaterialSurf(usuario);


        textinfowindsurf.setText("Tabla: " + MaterialWindSurf[0] + System.getProperty("line.separator")
                + "Vela: " + MaterialWindSurf[1]);

        textinfokitsurf.setText("Tabla: " + MaterialKiteSurf[0] + System.getProperty("line.separator")
                + "Cometa: " + MaterialKiteSurf[1]);

        textinfoSurf.setText("Tabla: " + MaterialSurf[0] + "/" + MaterialSurf[1]);


    }
    //Log.i(getClass().getSimpleName(), usuario.getUsernombre());
    //Toast.makeText(getApplicationContext(), "Hello AbhiAndroid..!!!", Toast.LENGTH_LONG).show();  // display a toast message
    //((AppQuatic) this.getApplication()).setListaDeSpots(ListaDeSpots)
    //System.getProperty("line.separator")

}





