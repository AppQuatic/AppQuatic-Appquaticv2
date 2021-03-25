package com.pfgunir.appquatic.Core;

import android.util.Log;

public class Usuario {


    private String user_id;
    private String usernombre;

    private String pass;
    private String altura;
    private String peso;
    private int modofavoritos;
    private String avatar;
    private Double masaCorporal;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsernombre() {
        return usernombre;
    }

    public void setUsernombre(String usernombre) {
        this.usernombre = usernombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public int getModofavoritos() {
        return modofavoritos;
    }

    public void setModofavoritos(int modofavoritos) {
        this.modofavoritos = modofavoritos;
    }



    public Usuario(String user_id) {
        this.user_id = user_id;
    }

    public int getMasaCorporal() {
       int imc=-1;
        Double masaCorporal= Double.valueOf( this.peso) / Math.pow (Double.valueOf( this.altura),2) ;

        if(masaCorporal<Constantes.Masa_Corporal_baja){
            imc=0;
            return imc;
        }
        if(masaCorporal>Constantes.Masa_Corporal_baja && masaCorporal<=Constantes.Masa_Corporal_alta){
            imc=1;
            return imc;
        }
        if(masaCorporal>Constantes.Masa_Corporal_alta){
            imc=2;
            return imc;
        }


        Log.i(getClass().getSimpleName(), masaCorporal.toString());

        return imc;
    }

    public void setMasaCorporal(Double masaCorporal) {
        this.masaCorporal = masaCorporal;
    }
}
