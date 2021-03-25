package com.pfgunir.appquatic.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfgunir.appquatic.Core.AppQuatic;
import com.pfgunir.appquatic.Core.DataBaseHelper;
import com.pfgunir.appquatic.Core.Spot;
import com.pfgunir.appquatic.Core.Usuario;
import com.pfgunir.appquatic.R;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {



    final DataBaseHelper database = new DataBaseHelper(this);
    ArrayList<Spot> ListaDeSpots ;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        getSupportActionBar().setTitle(getSupportActionBar().getTitle());
        getSupportActionBar().setSubtitle("Menú principal");
        recuperaUsuario();
        recuperaSpots();

        String resultado ="AppQuatic Info: "+System.getProperty("line.separator") + "Modo Favoritos " + usuario.getModofavoritos()
                +System.getProperty("line.separator") + ListaDeSpots.size()+ " Spots cargados";
        TextView txtresultado = (TextView) findViewById(R.id.menu_principal_textView_resultado);
        txtresultado.setText(resultado);
        try {
            actualizaSpots(ListaDeSpots);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        ImageView btLista = (ImageView) findViewById(R.id.menu_principal_imageview_lista_spots);

        btLista.setOnClickListener(this);
        ImageView btmap = (ImageView) findViewById(R.id.menu_principal_imageview_mapa_spots);

        btmap.setOnClickListener(this);

        ImageView btpreferencias = (ImageView) findViewById(R.id.menu_principal_imageview_preferencias);

        btpreferencias.setOnClickListener(this);

        ImageView btinformacion = (ImageView) findViewById(R.id.menu_principal_imageview_informacion);

        btinformacion.setOnClickListener(this);
    }




    private void actualizaSpots(ArrayList<Spot> listaDeSpots_a_actualizar) throws ExecutionException, InterruptedException {

        DescargaConBarraUi descarga = new DescargaConBarraUi(this, this, this.getString(R.string.barra_progreso_descargando_lecturas), ListaDeSpots);
        Object result = descarga.execute().get();


        /*
        int i =0;
        for(i =0;listaDeSpots_a_actualizar.size()>i;i++){
            txtresultado.setText(txtresultado.getText()+ " "+ ListaDeSpots.get(i).getNombreSpot() + " " + ListaDeSpots.get(i).getDatosOpenWeather().getDescription()+  ListaDeSpots.get(i).getDatosOpenWeather().getTemp() +" " + ListaDeSpots.get(i).getDatosAemet().getElaborado() + System.getProperty("line.separator") );

        }

         */

    }



    private void recuperaSpots() {



        ListaDeSpots= ((AppQuatic) this.getApplication()).getListaDeSpots();

    }

    private void recuperaUsuario() {

        usuario = ((AppQuatic) getApplication()).getUsuario();



        TextView txtusuario = (TextView) findViewById(R.id.menu_principal_textView_nombre_usuario);
        txtusuario.setText(usuario.getUsernombre());

        ImageView imgavatar = (ImageView) findViewById(R.id.menu_principal_imageview_avatar);
        String ruta_imagen ="icono_avatar_" +usuario.getAvatar();
        Log.i(getClass().getSimpleName(), ruta_imagen);
        Integer id_imagen = getResources().getIdentifier(ruta_imagen,"mipmap",getPackageName());
        imgavatar.setImageResource(id_imagen);



    }

    @Override
    public void onClick(View v) {
        Log.e(getClass().getSimpleName(), "on Click General");

        switch (v.getId()) {
            case R.id.menu_principal_imageview_lista_spots:



                lanzarActivity(ListaSpots.class);


                break;

            case R.id.menu_principal_imageview_mapa_spots:



                lanzarActivity(MapsActivity.class);

                break;
            case R.id.menu_principal_imageview_preferencias:



                lanzarActivity(Preferencias.class);

                break;
            case R.id.menu_principal_imageview_informacion:



                lanzarActivity(Informacion.class);

                break;
        }

    }


private void lanzarActivity( Class clase){


    Intent i = new Intent(this, clase);

    startActivity(i);
}

    //Log.i(getClass().getSimpleName(), usuario.getUsernombre());
    //Toast.makeText(getApplicationContext(), "Hello AbhiAndroid..!!!", Toast.LENGTH_LONG).show();  // display a toast message
    //((AppQuatic) this.getApplication()).setListaDeSpots(ListaDeSpots);
    //System.getProperty("line.separator")

}