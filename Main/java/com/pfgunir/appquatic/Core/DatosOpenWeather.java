package com.pfgunir.appquatic.Core;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DatosOpenWeather {

    private double lat;
    private double lon;

    public double temp;
    public double feels_like;
    public int pressure;
    public int humidity;
    public int clouds;
    public int visibility;
    public double wind_speed;
    public int wind_deg;


    public String main;
    public String description;
    public String icon;


    public DatosOpenWeather(double lat, double lon) {
        this.lat =lat;
        this.lon =lon;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
    }



    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void DescargaAsyncDatosOpenWeather() throws IOException, JSONException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat="+String.valueOf( this.getLat())+"&lon="+String.valueOf( this.getLon())+"&units=metric&lang=es&appid=" + Constantes.KEYOPENWEATHER);
       // JSONObject json = new JSONObject(IOUtils.toString(new URL("https://graph.facebook.com/me"), Charset.forName("UTF-8")));

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        Log.i(getClass().getSimpleName(),url.toString());


        reader = new BufferedReader(new InputStreamReader(stream, "ISO-8859-1"));


        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
            //Log.i("Response: ", "> " + line);   //here u ll get whole response...... :-)

        }
        JSONObject jsonObjectDatos = new JSONObject(buffer.toString());
        jsonObjectDatos = (JSONObject) jsonObjectDatos.get("current");

        this.setTemp(Double.valueOf( jsonObjectDatos.get("temp").toString()));
        this.setWind_speed(Double.valueOf( jsonObjectDatos.get("wind_speed").toString()));
        this.setWind_deg(Integer.valueOf( jsonObjectDatos.get("wind_deg").toString()));

        JSONArray jsonWather = new JSONArray(jsonObjectDatos.getString("weather"));
        JSONObject jsonObjectDatosWather = jsonWather.getJSONObject(0);

        //this.setId_Icono_Weather(jsonObjectDatosWather.get("id").toString());
        this.setDescription(jsonObjectDatosWather.get("description").toString());
        this.setIcon(jsonObjectDatosWather.get("icon").toString());

    }

    public String getFuerzaVientoIcono() {

        String fuerzaVientoicono="0";
        if((this.wind_speed)<2.5){
            fuerzaVientoicono="0";

        }

        if(this.wind_speed>2.5 && (this.wind_speed)<= 7 ){
            fuerzaVientoicono="1";

        }
        if((this.wind_speed)> 7 ){
            fuerzaVientoicono="2";

        }
        return fuerzaVientoicono;



    }

    public String getDireccionViento() {
        return convertDegreeToCardinalDirection(this.wind_deg);
    }
    private String convertDegreeToCardinalDirection(int directionInDegrees){
        String cardinalDirection = null;
        if( (directionInDegrees >= 348.75) && (directionInDegrees <= 360) ||
                (directionInDegrees >= 0) && (directionInDegrees <= 11.25)    ){
            cardinalDirection = "N";
        } else if( (directionInDegrees >= 11.25 ) && (directionInDegrees <= 33.75)){
            cardinalDirection = "NNE";
        } else if( (directionInDegrees >= 33.75 ) &&(directionInDegrees <= 56.25)){
            cardinalDirection = "NE";
        } else if( (directionInDegrees >= 56.25 ) && (directionInDegrees <= 78.75)){
            cardinalDirection = "ENE";
        } else if( (directionInDegrees >= 78.75 ) && (directionInDegrees <= 101.25) ){
            cardinalDirection = "E";
        } else if( (directionInDegrees >= 101.25) && (directionInDegrees <= 123.75) ){
            cardinalDirection = "ESE";
        } else if( (directionInDegrees >= 123.75) && (directionInDegrees <= 146.25) ){
            cardinalDirection = "SE";
        } else if( (directionInDegrees >= 146.25) && (directionInDegrees <= 168.75) ){
            cardinalDirection = "SSE";
        } else if( (directionInDegrees >= 168.75) && (directionInDegrees <= 191.25) ){
            cardinalDirection = "S";
        } else if( (directionInDegrees >= 191.25) && (directionInDegrees <= 213.75) ){
            cardinalDirection = "SSW";
        } else if( (directionInDegrees >= 213.75) && (directionInDegrees <= 236.25) ){
            cardinalDirection = "SW";
        } else if( (directionInDegrees >= 236.25) && (directionInDegrees <= 258.75) ){
            cardinalDirection = "WSW";
        } else if( (directionInDegrees >= 258.75) && (directionInDegrees <= 281.25) ){
            cardinalDirection = "W";
        } else if( (directionInDegrees >= 281.25) && (directionInDegrees <= 303.75) ){
            cardinalDirection = "WNW";
        } else if( (directionInDegrees >= 303.75) && (directionInDegrees <= 326.25) ){
            cardinalDirection = "NW";
        } else if( (directionInDegrees >= 326.25) && (directionInDegrees <= 348.75) ){
            cardinalDirection = "NNW";
        } else {
            cardinalDirection = "?";
        }

        return cardinalDirection;
    }
}
