package com.pfgunir.appquatic.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pfgunir.appquatic.Core.AppQuatic;
import com.pfgunir.appquatic.Core.Constantes;
import com.pfgunir.appquatic.Core.DataBaseHelper;
import com.pfgunir.appquatic.Core.Spot;
import com.pfgunir.appquatic.Core.Usuario;
import com.pfgunir.appquatic.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Usuario usuario;
    private DataBaseHelper database = new DataBaseHelper(this);
    ArrayList<Spot> ListaDeSpots = new ArrayList<Spot>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        database.createDB();

        TextView txtinfologin = (TextView) findViewById(R.id.main_activity_textView_resultado_login);
        txtinfologin.setText(R.string.introduce_credenciales);


        Button BTNentrar = (Button) findViewById(R.id.main_activity_imageButton_login);
        BTNentrar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {


                String user = ((EditText) findViewById(R.id.main_activity_textView_nombre_usuario_input)).getText().toString();
                String contra = ((EditText) findViewById(R.id.main_activity_editTextTextPassword_input)).getText().toString();
                String informaciondeprocesos = "";
                usuario = database.canloggin(user, contra);


                if (usuario != null) {
                    txtinfologin.setTextColor(Color.BLUE);
                    informaciondeprocesos = getString(R.string.acceso_usuario_concedido) + " " + usuario.getUsernombre() +System.getProperty("line.separator") +"Cargando Datos ";
                    ((AppQuatic) getApplication()).setUsuario(usuario);
                    recuperaSpots();


                    lanzarActivity(MenuPrincipal.class);


                } else {
                    txtinfologin.setTextColor(Color.RED);
                    informaciondeprocesos = getString(R.string.acceso_usuario_denegado);


                }
                txtinfologin.setText(informaciondeprocesos);
            }
        });


    }



    private void recuperaSpots() {

        ListaDeSpots=  database.getSpots();


        if(usuario.getModofavoritos()==1) {
            ArrayList lista_id_favoritos = database.getFavoritos(usuario.getUser_id());
            if(lista_id_favoritos!=null){

                ArrayList <Spot> lista_spots_favoritos= new ArrayList<Spot>();

                for(int i = 0; i<lista_id_favoritos.size();i++){

                    for(int y=0;y<ListaDeSpots.size();y++ ){
                        if(lista_id_favoritos.get(i).equals(ListaDeSpots.get(y).getIdspot())){
                            lista_spots_favoritos.add(ListaDeSpots.get(y));
                            }
                    }
                }

                ListaDeSpots=lista_spots_favoritos;
                ((AppQuatic) this.getApplication()).setListaDeSpots(ListaDeSpots);

            }
            else{
                ListaDeSpots=  database.getSpots();
                usuario.setModofavoritos(0);
                ((AppQuatic) this.getApplication()).setListaDeSpots(ListaDeSpots);

            }

        }
        else{

        }

        ((AppQuatic) this.getApplication()).setListaDeSpots(ListaDeSpots);

    }

    private void lanzarActivity (Class clase) {



        Intent i = new Intent(this, clase);

        //i.putExtra("ExtrasUsuario", (Serializable) usuario);
        startActivity(i);



    }

    //Log.i(getClass().getSimpleName(), usuario.getUsernombre());
    //System.getProperty("line.separator")
}