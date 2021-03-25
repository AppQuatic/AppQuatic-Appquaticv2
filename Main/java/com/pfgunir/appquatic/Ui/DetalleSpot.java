package com.pfgunir.appquatic.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pfgunir.appquatic.Core.AppQuatic;
import com.pfgunir.appquatic.Core.DataBaseHelper;
import com.pfgunir.appquatic.Core.Spot;
import com.pfgunir.appquatic.Core.Usuario;
import com.pfgunir.appquatic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class DetalleSpot extends AppCompatActivity {

    Spot spot;
    Usuario usuario;
    ArrayList<Spot> ListaDeSpots;
    private DataBaseHelper database = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_spot);

        recogerExtras();
        recuperaUsuario();
        rellenaInfo(spot);

        getSupportActionBar().setTitle(getSupportActionBar().getTitle());
        getSupportActionBar().setSubtitle(spot.getNombreSpot() );

    }

    private void rellenaInfo(Spot spot) {

        ImageView imgSurfIcon = this.findViewById(R.id.detalle_spot_imageView_icono_surf);
        //Picasso.get().load(R.drawable.icono_windsurf).into(imgSurfIcon);
        //imgSurfIcon.setBackgroundColor(Color.GREEN);

        ImageView imgWindSurfIcon = this.findViewById(R.id.detalle_spot_imageView_icono_wind_surf);
        // imgWindSurfIcon.setBackgroundColor(Color.RED);

        ImageView imgKiteSurfIcon = this.findViewById(R.id.detalle_spot_imageView_icono_kite_surf);
        //imgKiteSurfIcon.setBackgroundColor(Color.YELLOW);


        TextView txtnombre = (TextView) this.findViewById(R.id.detalle_spot_textView_nombre_spot);
        txtnombre.setText(spot.getNombreSpot());
        txtnombre.setBackgroundColor(Color.CYAN);


        ImageView imgWeatherIcon = (ImageView) this.findViewById(R.id.detalle_spot_imageView_icono_open);
        Picasso.get().load("https://openweathermap.org/img/w/"+spot.getDatosOpenWeather().getIcon()+".png").into(imgWeatherIcon);
        TextView infoclima = (TextView) this.findViewById(R.id.detalle_spot_textView_Info_clima);
        infoclima.setText("\t"+spot.getDatosOpenWeather().getDescription()+ " Temp "+ spot.getDatosOpenWeather().getTemp()+"°");

        TextView infoviento = (TextView) this.findViewById(R.id.detalle_spot_textView_Info_viento);
        infoviento.setText("Viento:"+"\t"+spot.getDatosOpenWeather().getDireccionViento()+" " + spot.getDatosOpenWeather().getWind_deg() +"° Fuerza " + spot.getDatosOpenWeather().getWind_speed()+ " m/s");

        String ruta_imagen="";
        int id_imagen;
        ImageView imgvientoIcon = this.findViewById(R.id.detalle_spot_imageView_icono_viento);
        ruta_imagen ="icono_viento_" +spot.getDatosOpenWeather().getFuerzaVientoIcono() ;
        id_imagen= getResources().getIdentifier(ruta_imagen,"drawable",getPackageName());
        Picasso.get().load(id_imagen).into(imgvientoIcon);

        ImageView imgDireccionVientoIcon = this.findViewById(R.id.detalle_spot_imageView_icono_direccion_viento);
        ruta_imagen ="icono_" +spot.getDatosOpenWeather().getDireccionViento().toLowerCase()+"_wind" ;
        id_imagen= getResources().getIdentifier(ruta_imagen,"drawable",getPackageName());
        Picasso.get().load(id_imagen).into(imgDireccionVientoIcon);


        TextView infomar = (TextView) this.findViewById(R.id.detalle_spot_textView_Info_mar);
        infomar.setText("Oleje:" +"\t"+ spot.getDatosAemet().getOleaje());


        ImageView imgoleajeIcon = this.findViewById(R.id.detalle_spot_imageView_icono_mar);
        ruta_imagen = "icono_olas_" + spot.getDatosAemet().getOleajeIcono();
        id_imagen= getResources().getIdentifier(ruta_imagen,"drawable",getPackageName());
        Picasso.get().load(id_imagen).into(imgoleajeIcon);


        rellena_material_deportes(spot);
        colorea_iconos_deportes( spot);

    }


    public void recogerExtras() {
//Aquí recogemos y tratamos los parámetros
        Bundle extras = getIntent().getExtras();




        String spot_id =  extras.getString("spot_id");

        ListaDeSpots=((AppQuatic) this.getApplication()).getListaDeSpots();

        for(int i=0;i< ListaDeSpots.size();i++){

            if(ListaDeSpots.get(i).getIdspot().equals(spot_id)){
                spot = ListaDeSpots.get(i);

            }
        }


    }
    private void recuperaUsuario() {

        usuario = ((AppQuatic) getApplication()).getUsuario();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalle_spot_menu, menu);

        int i = database.isSpotFavorito(spot.getIdspot());

        if(i==1){
            menu.findItem(R.id.action_detalle_spot_favorito).setIcon(android.R.drawable.btn_star_big_on);

        }
        else{
            menu.findItem(R.id.action_detalle_spot_favorito).setIcon(android.R.drawable.btn_star_big_off);
        }

        Log.i(getClass().getSimpleName(), "HOOOLA");

        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_detalle_spot_favorito:

                ContentValues cv = new ContentValues();
                cv.put("user_id", usuario.getUser_id()); //These Fields should be your String values of actual column names
                cv.put("spot_id",  spot.getIdspot());


                int i = database.addFavorito(cv);
                if(i>1){
                    item.setIcon(android.R.drawable.btn_star_big_on);

                }
                else{
                    item.setIcon(android.R.drawable.btn_star_big_off);

                }

                Log.e(getClass().getSimpleName(), "resultado --- agregar Spot " + i );


                break;
            case R.id.action_detalle_spot_mostrar_en_mapa:

                lanzarActivity( MapsActivity.class);

                break;
            case R.id.action_detalle_spot_actualizar:


                ArrayList <Spot> spot_a_actualizar= new ArrayList();
                spot_a_actualizar.add(spot);
                DescargaConBarraUi descarga = new DescargaConBarraUi(this, this, this.getString(R.string.barra_progreso_descargando_lecturas) + spot.getNombreSpot(), spot_a_actualizar);
                try {
                    Object result = descarga.execute().get();
                    //Log.i(getClass().getSimpleName(), result.toString() );
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.rellenaInfo(spot);

                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void lanzarActivity( Class<MapsActivity> mapsActivityClass) {

        Intent i = new Intent(DetalleSpot.this, MapsActivity.class);

        i.putExtra("spot_id", spot.getIdspot());
        finish();
        startActivity(i);
    }



    private void colorea_iconos_deportes( Spot spot) {

        ImageView img_icono_windsurf = this.findViewById(R.id.detalle_spot_imageView_icono_wind_surf);
        TextView textinfowindsurf = (TextView)
                this.findViewById(R.id.detalle_textView_material_windsurf);

        TextView textdetallewindsurf = (TextView)
                this.findViewById(R.id.detalle_spot_textView_windsurf);




        switch (spot.getEstadoWindSurf()) {
            case 0:
                break;
            case 1:
                img_icono_windsurf.setBackgroundColor(Color.RED);
                textinfowindsurf.setBackgroundColor(Color.RED);
                textdetallewindsurf.setBackgroundColor(Color.RED);
                textinfowindsurf.setText("NO practicable");;
                break;
            case 2:
                img_icono_windsurf.setBackgroundColor(Color.YELLOW);
                textinfowindsurf.setBackgroundColor(Color.YELLOW);
                textdetallewindsurf.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                img_icono_windsurf.setBackgroundColor(Color.GREEN);
                textinfowindsurf.setBackgroundColor(Color.GREEN);
                textdetallewindsurf.setBackgroundColor(Color.GREEN);
                break;
            default:
                img_icono_windsurf.setBackgroundColor(Color.BLACK);
                textinfowindsurf.setBackgroundColor(Color.BLACK);
                textdetallewindsurf.setBackgroundColor(Color.BLACK);
                textinfowindsurf.setText("NO practicable");;
                break;

        }



        ImageView img_icono_Kitesurf = this.findViewById(R.id.detalle_spot_imageView_icono_kite_surf);
        TextView textinfokitsurf = (TextView)
                this.findViewById(R.id.detalle_textView_material_kitesurf);

        TextView textdetalleKitesurf = (TextView)
                this.findViewById(R.id.detalle_spot_textView_kitesurf);



        switch (spot.getEstadoKiteSurf()) {
            case 0:
                break;
            case 1:
                img_icono_Kitesurf.setBackgroundColor(Color.RED);
                textinfokitsurf.setBackgroundColor(Color.RED);
                textdetalleKitesurf.setBackgroundColor(Color.RED);
                textinfokitsurf.setText("NO practicable");
                break;
            case 2:
                img_icono_Kitesurf.setBackgroundColor(Color.YELLOW);
                textinfokitsurf.setBackgroundColor(Color.YELLOW);
                textdetalleKitesurf.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                img_icono_Kitesurf.setBackgroundColor(Color.GREEN);
                textinfokitsurf.setBackgroundColor(Color.GREEN);
                textdetalleKitesurf.setBackgroundColor(Color.GREEN);
                break;
            default:
                img_icono_Kitesurf.setBackgroundColor(Color.BLACK);
                textinfokitsurf.setBackgroundColor(Color.BLACK);
                textdetalleKitesurf.setBackgroundColor(Color.BLACK);
                textinfokitsurf.setText("NO practicable");
                break;


        }


        ImageView img_icono_Surf = this.findViewById(R.id.detalle_spot_imageView_icono_surf);
        TextView textinfoSurf = (TextView)
                this.findViewById(R.id.detalle_textView_material_surf);
        textinfoSurf.setText("Tabla: 18/7" );
        TextView textdetalleSurf = (TextView)
                this.findViewById(R.id.detalle_spot_textView_surf);


        switch (spot.getEstadoSurf()) {
            case 0:
                break;
            case 1:
                img_icono_Surf.setBackgroundColor(Color.RED);
                textinfoSurf.setBackgroundColor(Color.RED);
                textdetalleSurf.setBackgroundColor(Color.RED);
                textinfoSurf.setText("NO practicable");
                break;
            case 2:
                img_icono_Surf.setBackgroundColor(Color.YELLOW);
                textinfoSurf.setBackgroundColor(Color.YELLOW);
                textdetalleSurf.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                img_icono_Surf.setBackgroundColor(Color.GREEN);
                textinfoSurf.setBackgroundColor(Color.GREEN);
                textdetalleSurf.setBackgroundColor(Color.GREEN);
                break;
            default:
                img_icono_Surf.setBackgroundColor(Color.BLACK);
                textinfoSurf.setBackgroundColor(Color.BLACK);
                textdetalleSurf.setBackgroundColor(Color.BLACK);
                textinfoSurf.setText("NO practicable");

                break;


        }


    }


    private void rellena_material_deportes( Spot spot) {


        TextView textmaterialwindsurf = (TextView)
                this.findViewById(R.id.detalle_textView_material_windsurf);
        TextView textinfokitsurf = (TextView)
                this.findViewById(R.id.detalle_textView_material_kitesurf);
        TextView textinfoSurf = (TextView)
                this.findViewById(R.id.detalle_textView_material_surf);

        int[] MaterialWindSurf = spot.getMaterialWindSurf(usuario);
        int[] MaterialKiteSurf = spot.getMaterialKiteSurf(usuario);
        int[] MaterialSurf = spot.getMaterialSurf(usuario);




        textmaterialwindsurf.setText("Tabla: "+ MaterialWindSurf[0] + System.getProperty("line.separator")
        + "Vela: " + MaterialWindSurf[1] );

        textinfokitsurf.setText("Tabla: "+ MaterialKiteSurf[0] + System.getProperty("line.separator")
                + "Comenta: " + MaterialKiteSurf[1] );

        textinfoSurf.setText("Tabla: "+ MaterialSurf[0] +"/"+MaterialSurf[1] );



    }
    //Log.i(getClass().getSimpleName(), usuario.getUsernombre());
    //Toast.makeText(getApplicationContext(), "Hello AbhiAndroid..!!!", Toast.LENGTH_LONG).show();  // display a toast message
    //((AppQuatic) this.getApplication()).setListaDeSpots(ListaDeSpots)
    //System.getProperty("line.separator")

}