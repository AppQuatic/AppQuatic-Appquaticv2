package com.pfgunir.appquatic.Core;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class DatosMeteoAemet  {


    String idDatosAemet;
    String idPlaya;
    String nombre;
    String elaborado;
    String localidad;
    String periodoValidez;

    String estadoCielo_manyana;
    String estadoCielo_tarde;

    String viento_manyana;
    String viento_tarde;

    String oleaje_manyana;
    String oleaje_tarde;


    String tMaxima_manyana;
    String tMaxima_tarde;

    String sTermica_manyana;
    String sTermica_tarde;

    String tAgua_manyana;
    String tAgua_tarde;

    String uvMax_manyana;
    String uvMax_tarde;

    String Viento_manyana_icono;
    String Viento_tarde_icono;
    String Oleaje_manyana_icono;
    String Oleaje_tarde_icono;


    public DatosMeteoAemet(String idPlaya) {
        this.idPlaya =idPlaya;

    }


    public String getIdDatosAemet() {
        return idDatosAemet;
    }

    public void setIdDatosAemet(String idDatosAemet) {
        this.idDatosAemet = idDatosAemet;
    }

    public String getIdPlaya() {
        return idPlaya;
    }

    public void setIdPlaya(String idPlaya) {
        this.idPlaya = idPlaya;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getElaborado() {
        return elaborado;
    }

    public void setElaborado(String elaborado) {
        this.elaborado = elaborado;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPeriodoValidez() {
        return periodoValidez;
    }

    public void setPeriodoValidez(String periodoValidez) {
        this.periodoValidez = periodoValidez;
    }

    public String getEstadoCielo_manyana() {
        return estadoCielo_manyana;
    }

    public void setEstadoCielo_manyana(String estadoCielo_manyana) {
        this.estadoCielo_manyana = estadoCielo_manyana;
    }

    public String getEstadoCielo_tarde() {
        return estadoCielo_tarde;
    }

    public void setEstadoCielo_tarde(String estadoCielo_tarde) {
        this.estadoCielo_tarde = estadoCielo_tarde;
    }

    public String getViento_manyana() {
        return viento_manyana;
    }

    public void setViento_manyana(String viento_manyana) {
        this.viento_manyana = viento_manyana;
    }

    public String getViento_tarde() {
        return viento_tarde;
    }

    public void setViento_tarde(String viento_tarde) {
        this.viento_tarde = viento_tarde;
    }

    public String getOleaje_manyana() {
        return oleaje_manyana;
    }

    public void setOleaje_manyana(String oleaje_manyana) {
        this.oleaje_manyana = oleaje_manyana;
    }

    public String getOleaje_tarde() {
        return oleaje_tarde;
    }

    public void setOleaje_tarde(String oleaje_tarde) {
        this.oleaje_tarde = oleaje_tarde;
    }

    public String gettMaxima_manyana() {
        return tMaxima_manyana;
    }

    public void settMaxima_manyana(String tMaxima_manyana) {
        this.tMaxima_manyana = tMaxima_manyana;
    }

    public String gettMaxima_tarde() {
        return tMaxima_tarde;
    }

    public void settMaxima_tarde(String tMaxima_tarde) {
        this.tMaxima_tarde = tMaxima_tarde;
    }

    public String getsTermica_manyana() {
        return sTermica_manyana;
    }

    public void setsTermica_manyana(String sTermica_manyana) {
        this.sTermica_manyana = sTermica_manyana;
    }

    public String getsTermica_tarde() {
        return sTermica_tarde;
    }

    public void setsTermica_tarde(String sTermica_tarde) {
        this.sTermica_tarde = sTermica_tarde;
    }

    public String gettAgua_manyana() {
        return tAgua_manyana;
    }

    public void settAgua_manyana(String tAgua_manyana) {
        this.tAgua_manyana = tAgua_manyana;
    }

    public String gettAgua_tarde() {
        return tAgua_tarde;
    }

    public void settAgua_tarde(String tAgua_tarde) {
        this.tAgua_tarde = tAgua_tarde;
    }

    public String getUvMax_manyana() {
        return uvMax_manyana;
    }

    public void setUvMax_manyana(String uvMax_manyana) {
        this.uvMax_manyana = uvMax_manyana;
    }

    public String getUvMax_tarde() {
        return uvMax_tarde;
    }

    public void setUvMax_tarde(String uvMax_tarde) {
        this.uvMax_tarde = uvMax_tarde;
    }

    public String getViento_manyana_icono() {
        return Viento_manyana_icono;
    }

    public void setViento_manyana_icono(String viento_manyana_icono) {
        Viento_manyana_icono = viento_manyana_icono;
    }

    public String getViento_tarde_icono() {
        return Viento_tarde_icono;
    }

    public void setViento_tarde_icono(String viento_tarde_icono) {
        Viento_tarde_icono = viento_tarde_icono;
    }

    public String getOleaje_manyana_icono() {
        return Oleaje_manyana_icono;
    }

    public void setOleaje_manyana_icono(String oleaje_manyana_icono) {
        Oleaje_manyana_icono = oleaje_manyana_icono;
    }

    public String getOleaje_tarde_icono() {
        return Oleaje_tarde_icono;
    }

    public void setOleaje_tarde_icono(String oleaje_tarde_icono) {
        Oleaje_tarde_icono = oleaje_tarde_icono;
    }

    public void DescargaAsyncDatosAemet() throws IOException, JSONException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        URL url = new URL("https://opendata.aemet.es/opendata/api/prediccion/especifica/playa/" + this.getIdPlaya() + "?api_key=" + Constantes.KEYAMET);

        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();

        reader = new BufferedReader(new InputStreamReader(stream, "ISO-8859-1"));


        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
            Log.i("Response: ", "> " + line);   //here u ll get whole response...... :-)

        }
        JSONObject jsonObjectUrl = new JSONObject(buffer.toString());
        URL url2 = new URL((String) jsonObjectUrl.get("datos"));
        Log.i(getClass().getSimpleName(),url2.toString());
        connection = (HttpURLConnection) url2.openConnection();
        connection.connect();
        stream = connection.getInputStream();

        reader = new BufferedReader(new InputStreamReader(stream, "ISO-8859-1"));

        buffer = new StringBuffer();
        line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
            //Log.i("Response: ", "> " + line);   //here u ll get whole response...... :-)

        }

        JSONArray jsonArrayTodosDatosPlaya = new JSONArray(buffer.toString());
        this.setElaborado(jsonArrayTodosDatosPlaya.getJSONObject(0).getString("elaborado"));
        JSONObject jsonObjectDatosPlayaPrediccion = new JSONObject(jsonArrayTodosDatosPlaya.getJSONObject(0).getString("prediccion"));


        JSONArray jsonArrayDatosPlayaDia = new JSONArray(jsonObjectDatosPlayaPrediccion.getString("dia"));

        JSONObject jsonObjectDatosFinal = jsonArrayDatosPlayaDia.getJSONObject(0);

        JSONObject jsonObjectDatosEstadocielo = jsonObjectDatosFinal.getJSONObject("estadoCielo");

        this.setEstadoCielo_manyana(jsonObjectDatosEstadocielo.getString("descripcion1"));
        this.setEstadoCielo_tarde(jsonObjectDatosEstadocielo.getString("descripcion2"));
        JSONObject jsonObjectDatosviento = jsonObjectDatosFinal.getJSONObject("viento");
        this.setViento_manyana(jsonObjectDatosviento.getString("descripcion1"));
        this.setViento_manyana_icono(jsonObjectDatosviento.getString("f1"));
        this.setViento_tarde(jsonObjectDatosviento.getString("descripcion2"));
        this.setViento_tarde_icono(jsonObjectDatosviento.getString("f2"));

        JSONObject jsonObjectDatosoleaje = jsonObjectDatosFinal.getJSONObject("oleaje");

        this.setOleaje_manyana((jsonObjectDatosoleaje.getString("descripcion1")));
        this.setOleaje_manyana_icono((jsonObjectDatosoleaje.getString("f1")));
        this.setOleaje_tarde((jsonObjectDatosoleaje.getString("descripcion2")));
        this.setOleaje_tarde_icono((jsonObjectDatosoleaje.getString("f2")));

    }

    public int EsManyanaoTarde (){

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            // Log.i(getClass().getSimpleName(), "--> Es por la mañana ");
            return 0;
        }

        else if(timeOfDay >= 12 && timeOfDay < 24){
            //Log.i(getClass().getSimpleName(), "--> Es por la Tarde ");
            return 1;
        }
        else{
            //Log.i(getClass().getSimpleName(), "--> No es nunca ");
            return 2;

        }

    }


    public String getOleaje() {

        if(EsManyanaoTarde()==0){
            return oleaje_manyana;
        }
        else if(EsManyanaoTarde()==1){
            return oleaje_tarde;
        }
        else{
            return "problema de datos";
        }
    }

    public String getOleajeIcono() {
        if(EsManyanaoTarde()==0){
            return this.Oleaje_manyana_icono;
        }
        else if(EsManyanaoTarde()==1){
            return this.Oleaje_tarde_icono;
        }
        else{
            return "problema de datos";
        }
    }
    public int estadoOleaje(){
        int oleaje=0;

        if(this.getOleaje().equals(Constantes.Debil)){oleaje=1;}
        if(this.getOleaje().equals(Constantes.Moderado)){oleaje=2;}
        if(this.getOleaje().equals(Constantes.Fuerte)){oleaje=3;}
        return oleaje;
    }
}
