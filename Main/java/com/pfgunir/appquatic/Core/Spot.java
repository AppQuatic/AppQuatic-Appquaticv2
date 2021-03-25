package com.pfgunir.appquatic.Core;

import android.graphics.Color;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class Spot {


    private String nombreSpot;
    private double Lat;
    private double Lon;
    private String IdPlayaAemet;
    private String idspot;
    DatosOpenWeather datosOpenWeather;
    DatosMeteoAemet datosAemet;


    public Spot(String idspot) {
        this.idspot = idspot;
    }

    public String getIdspot() {
        return idspot;
    }

    public void setIdspot(String idspot) {
        this.idspot = idspot;
    }

    public String getNombreSpot() {
        return nombreSpot;
    }

    public void setNombreSpot(String nombreSpot) {
        this.nombreSpot = nombreSpot;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public String getIdPlayaAemet() {
        return IdPlayaAemet;
    }

    public void setIdPlayaAemet(String idPlayaAemet) {
        IdPlayaAemet = idPlayaAemet;
    }

    public DatosOpenWeather getDatosOpenWeather() {
        return datosOpenWeather;
    }

    public void setDatosOpenWeather(DatosOpenWeather datosOpenWeather) {
        this.datosOpenWeather = datosOpenWeather;
    }


    public DatosMeteoAemet getDatosAemet() {
        return datosAemet;
    }

    public void setDatosAemet(DatosMeteoAemet datosAemet) {
        this.datosAemet = datosAemet;
    }

    public void DescargaDatosOpen(double lat, double lon) throws IOException, JSONException {
        this.datosOpenWeather = new DatosOpenWeather(this.getLat(), this.getLon());
        this.datosOpenWeather.DescargaAsyncDatosOpenWeather();

    }

    public void DescargaDatosAemet(String idPlayaAemet) throws IOException, JSONException {
        this.datosAemet = new DatosMeteoAemet(idPlayaAemet);
        this.datosAemet.DescargaAsyncDatosAemet();

    }

    public int getEstadoWindSurf() {
        int estado = 0;
        if (this.datosOpenWeather.getWind_speed() < Constantes.Viento_mini_Windsurf || this.datosOpenWeather.getWind_speed() > Constantes.Viento_max_Windsurf) {
            estado = 1;
            return estado;
        }
        if (this.datosOpenWeather.getWind_speed() > Constantes.Viento_mini_Windsurf && this.datosOpenWeather.getWind_speed() < Constantes.Viento_max_Windsurf) {

            if (this.getDatosAemet().getOleaje().equals(Constantes.Fuerte)) {
                estado = 3;
            } else {
                estado = 2;
            }
            return estado;
        }

        return estado;

    }

    public int getEstadoKiteSurf() {
        int estado = 0;
        if (this.datosOpenWeather.getWind_speed() < Constantes.Viento_mini_Kitesurf || this.datosOpenWeather.getWind_speed() > Constantes.Viento_max_Kitesurf) {
            estado = 1;
            return estado;
        }
        if (this.datosOpenWeather.getWind_speed() > Constantes.Viento_mini_Kitesurf && this.datosOpenWeather.getWind_speed() < Constantes.Viento_max_Kitesurf) {

            if (this.getDatosAemet().getOleaje().equals(Constantes.Moderado)) {
                estado = 3;
            } else {
                estado = 2;
            }
            return estado;
        }

        return estado;

    }


    public int getEstadoSurf() {
        int estado = 0;
        if (this.datosOpenWeather.getWind_speed() > Constantes.Viento_max_Surf) {
            estado = 1;
            return estado;
        }
        if (this.getDatosAemet().getOleaje().equals(Constantes.Debil)) {
            estado = 1;
            return estado;
        }
        if (this.getDatosAemet().getOleaje().equals(Constantes.Fuerte)) {
            estado = 2;
        }
        if (this.getDatosAemet().getOleaje().equals(Constantes.Moderado)) {
            estado = 3;
        }


        return estado;

    }

    public int[] getMaterialWindSurf(Usuario usuario) {
        int[] MaterialWindSurf = new int[]{7, 7};
        if (this.datosOpenWeather.getWind_speed() < Constantes.Viento_mini_Windsurf || this.datosOpenWeather.getWind_speed() > Constantes.Viento_max_Windsurf) {

            switch (usuario.getMasaCorporal()) {
                case 0:
                    MaterialWindSurf = new int[]{8, 6};


                    break;
                case 1:

                    MaterialWindSurf = new int[]{7, 7};
                    break;
                case 2:
                    MaterialWindSurf = new int[]{9, 7};

                    break;

            }
        } else {

            switch (usuario.getMasaCorporal()) {
                case 0:
                    MaterialWindSurf = new int[]{6, 7};

                    break;
                case 1:

                    MaterialWindSurf = new int[]{7, 8};
                    break;
                case 2:
                    MaterialWindSurf = new int[]{10, 8};

                    break;
            }


        }
        return MaterialWindSurf;
    }

    public int[] getMaterialKiteSurf(Usuario usuario) {
        int[] MaterialKiteSurf = new int[]{7, 7};
        if (this.datosOpenWeather.getWind_speed() < Constantes.Viento_mini_Kitesurf || this.datosOpenWeather.getWind_speed() > Constantes.Viento_max_Kitesurf) {

            switch (usuario.getMasaCorporal()) {
                case 0:
                    MaterialKiteSurf = new int[]{5, 5};


                    break;
                case 1:

                    MaterialKiteSurf = new int[]{7, 7};
                    break;
                case 2:
                    MaterialKiteSurf = new int[]{8, 8};

                    break;

            }
        } else {

            switch (usuario.getMasaCorporal()) {
                case 0:
                    MaterialKiteSurf = new int[]{5, 7};

                    break;
                case 1:

                    MaterialKiteSurf = new int[]{6, 7};
                    break;
                case 2:
                    MaterialKiteSurf = new int[]{7, 8};

                    break;
            }


        }
        return MaterialKiteSurf;
    }


    public int[] getMaterialSurf(Usuario usuario) {
        int[] MaterialKiteSurf = new int[]{7, 7};
        if (this.datosOpenWeather.getWind_speed() < Constantes.Viento_mini_Kitesurf || this.datosOpenWeather.getWind_speed() > Constantes.Viento_max_Kitesurf) {

            switch (usuario.getMasaCorporal()) {
                case 0:
                    MaterialKiteSurf = new int[]{4, 6};


                    break;
                case 1:

                    MaterialKiteSurf = new int[]{5, 7};
                    break;
                case 2:
                    MaterialKiteSurf = new int[]{9, 8};

                    break;

            }
        } else {

            switch (usuario.getMasaCorporal()) {
                case 0:
                    MaterialKiteSurf = new int[]{3, 4};

                    break;
                case 1:

                    MaterialKiteSurf = new int[]{4, 4};
                    break;
                case 2:
                    MaterialKiteSurf = new int[]{6, 7};

                    break;
            }


        }
        return MaterialKiteSurf;
    }
}


