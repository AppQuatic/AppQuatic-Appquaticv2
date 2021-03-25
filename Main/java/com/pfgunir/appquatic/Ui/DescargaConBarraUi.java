package com.pfgunir.appquatic.Ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.pfgunir.appquatic.Core.Spot;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class DescargaConBarraUi extends AsyncTask<Void, Integer, Void>
        implements DialogInterface {

    private ProgressDialog barraProgreso;

    /** Contexto. */
    private Context context;

    /** Actividad que lo ha solicitado */
    private Activity actividad;

    /** Mensaje a mostrar. */
    private String mensaje;

    ArrayList <Spot> lista_spots_descargar ;




    public DescargaConBarraUi(Context context, Activity actividad,
                              String mensaje, ArrayList <Spot> spots_a_descargar) {
        super();
        this.context = context;
        this.actividad = actividad;
        this.mensaje = mensaje;
        lista_spots_descargar = spots_a_descargar;
    }
    @Override
    protected Void doInBackground(Void... voids) {

        barraProgreso.setMessage("Descargando "+ lista_spots_descargar.size() +" Spots " );
        barraProgreso.setProgress(0);

        for(int i =0;lista_spots_descargar.size()>i;i++){
            try {
                lista_spots_descargar.get(i).DescargaDatosOpen(lista_spots_descargar.get(i).getLat(),lista_spots_descargar.get(i).getLon());
                lista_spots_descargar.get(i).DescargaDatosAemet(lista_spots_descargar.get(i).getIdPlayaAemet());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


           // Log.e(getClass().getSimpleName(), lista_spots_descargar.get(i).getNombreSpot() +" HEEEEY "+ lista_spots_descargar.get(i).getLat());
            barraProgreso.setProgress(i);

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        barraProgreso = new ProgressDialog(context);
        actividad
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        barraProgreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        String msg = mensaje;
        barraProgreso.setMessage(msg);
        barraProgreso.setMax(1);
        barraProgreso.setProgress(0);
        barraProgreso.setCanceledOnTouchOutside(false);
        if (actividad.isFinishing()== false){
            barraProgreso.show();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        actividad.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        if (barraProgreso != null) {
            barraProgreso.dismiss();
        }
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }
}
