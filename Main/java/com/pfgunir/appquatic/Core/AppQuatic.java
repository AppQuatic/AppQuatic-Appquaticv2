package com.pfgunir.appquatic.Core;

import android.app.Application;

import java.util.ArrayList;

public class AppQuatic extends Application {
   private ArrayList<Spot> ListaDeSpots = new ArrayList<Spot>();
    private ArrayList<Spot> ListaDeSpotsFavoritos = new ArrayList<Spot>();
   private Usuario usuario;

    public ArrayList<Spot> getListaDeSpots() {
        if(usuario.getModofavoritos()==1){
            return ListaDeSpotsFavoritos;
        }

        return ListaDeSpots;
    }

    public void setListaDeSpots(ArrayList<Spot> listaDeSpots) {
        if(usuario.getModofavoritos()==1){
            ListaDeSpotsFavoritos= listaDeSpots;
        }

        ListaDeSpots = listaDeSpots;
    }

    public ArrayList<Spot> getListaDeSpotsFavoritos() {
        return ListaDeSpotsFavoritos;
    }

    public void setListaDeSpotsFavoritos(ArrayList<Spot> listaDeSpotsFavoritos) {
        ListaDeSpotsFavoritos = listaDeSpotsFavoritos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
