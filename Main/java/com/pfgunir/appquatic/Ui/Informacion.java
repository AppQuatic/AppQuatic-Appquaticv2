package com.pfgunir.appquatic.Ui;

import androidx.appcompat.app.AppCompatActivity;
import com.pfgunir.appquatic.R;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Informacion extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomacion);
        getSupportActionBar().setTitle(getSupportActionBar().getTitle());
        getSupportActionBar().setSubtitle("Información");

        TextView txtinfodatos = (TextView) findViewById(R.id.informacion_textView_datos);
        txtinfodatos.setText("Datos climatológicos :"+ System.getProperty("line.separator") +"\t"+"OpenWeathermap.org" + System.getProperty("line.separator") +
                "Datos marítimos:"+ System.getProperty("line.separator") +"\t"+"Agencia Estatal de Meteorología-AEMET"+ System.getProperty("line.separator")
                +"\t"+"Gobierno de España ");


        ImageView cuadrado_verde = (ImageView) findViewById(R.id.informacion_imageView_cuadrado_verde);
        cuadrado_verde.setBackgroundColor(Color.GREEN);
        ImageView cuadrado_amarillo = (ImageView) findViewById(R.id.informacion_imageView_cuadrado_amarillo);
        cuadrado_amarillo.setBackgroundColor(Color.YELLOW);
        ImageView cuadrado_rojo = (ImageView) findViewById(R.id.informacion_imageView_cuadrado_rojo);
        cuadrado_rojo.setBackgroundColor(Color.RED);

    }

    //Log.i(getClass().getSimpleName(), usuario.getUsernombre());
    //System.getProperty("line.separator")
}