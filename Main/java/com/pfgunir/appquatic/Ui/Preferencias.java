package com.pfgunir.appquatic.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.pfgunir.appquatic.Core.AppQuatic;
import com.pfgunir.appquatic.Core.DataBaseHelper;
import com.pfgunir.appquatic.Core.Usuario;
import com.pfgunir.appquatic.R;

public class Preferencias extends AppCompatActivity {

    Usuario usuario;
    TextView textusuario;
    TextView textaltura;
    TextView textpeso;
    TextView resultadooperaciones;
    Switch botonfavoritos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        getSupportActionBar().setTitle(getSupportActionBar().getTitle());
        getSupportActionBar().setSubtitle("Preferencias");
        resultadooperaciones = (TextView) findViewById(R.id.preferencias_textView_resultado);
        recuperaUsuario();


        ImageButton BTNguardar = (ImageButton) findViewById(R.id.preferencias_imagebuton_guardar);
        BTNguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Compurebadatos()==true){

                    ContentValues cv = new ContentValues();
                    cv.put("altura", textaltura.getText().toString()); //These Fields should be your String values of actual column names
                    cv.put("peso",  textpeso.getText().toString());
                    if(botonfavoritos.isChecked()){
                        cv.put("modofavoritos", 1 );
                    }
                    else{
                        cv.put("modofavoritos", 0 );
                    }

                    /*
                    String Estado_favoritos;
                    if(botonfavoritos.isChecked())
                        Estado_favoritos = "true";
                    else
                        Estado_favoritos= "false";
                    cv.put("modofavoritos",Estado_favoritos);

                     */

                    final DataBaseHelper database = new DataBaseHelper(view.getContext());

                    int resultado = database.upDatePreferencias(cv,usuario.getUser_id());
                    if (resultado == 1){
                        Log.i(getClass().getSimpleName(), "resultado de update preferencias correcto"+ resultado);

                        usuario.setPeso(cv.getAsString("peso"));
                        usuario.setAltura(cv.getAsString("altura"));
                        usuario.setModofavoritos(cv.getAsInteger(("modofavoritos")));
                        ((AppQuatic) getApplication()).setUsuario(usuario);
                        resultadooperaciones.setText("Peferencias guardadas " );
                        resultadooperaciones.setTextColor(Color.GREEN);


                    }
                    else{
                        Log.i(getClass().getSimpleName(), "resultado de update preferencias fallo"+ resultado);
                        resultadooperaciones.setText("resultado de update preferencias fallo");
                    }
                }
                else {}



            }

        });
    }


    private void recuperaUsuario() {

        usuario = ((AppQuatic) getApplication()).getUsuario();



        TextView txtusuario = (TextView) findViewById(R.id.preferncias_textView_nombre_usuario);
        txtusuario.setText(usuario.getUsernombre());

        ImageView imgavatar = (ImageView) findViewById(R.id.preferencias_imageview_avatar);
        String ruta_imagen ="icono_avatar_" +usuario.getAvatar();
        Log.i(getClass().getSimpleName(), ruta_imagen);
        Integer id_imagen = getResources().getIdentifier(ruta_imagen,"mipmap",getPackageName());
        imgavatar.setImageResource(id_imagen);
        textaltura = (TextView)
                this.findViewById(R.id.preferencias_textView_in_altura);
        textaltura.setText(this.usuario.getAltura());

        textpeso = (TextView)
                this.findViewById(R.id.preferencias_textView_in_peso);
        textpeso.setText(this.usuario.getPeso());

        botonfavoritos =(Switch)   this.findViewById(R.id.preferencias_switch_favoritos );
        if(this.usuario.getModofavoritos()==1){
            botonfavoritos.setChecked(true);

        }
        else{
            botonfavoritos.setChecked(false);

        }



    }
    private boolean Compurebadatos (){

        boolean resultado = false;
        int altura;
        int peso;

        String problemas ="";


        try {
            altura = Integer.parseInt(textaltura.getText().toString());
            peso = Integer.parseInt(textpeso.getText().toString());
        }
        catch (NumberFormatException e)
        {
            altura = 0;
            peso = 0;
        }
        if(altura > 50 && altura < 300 && peso > 15 && peso < 200  ){
            resultado = true;
        }
        else{
            problemas = problemas + "La altura debe estar entre los 50 y los 300 Cm";
            problemas = problemas + System.getProperty("line.separator")+
                    "El peso debe estar entre los 15 y los 200 Kg";
            resultadooperaciones.setTextColor(Color.RED);
            resultadooperaciones.setText(problemas);
            resultado = false;
        }


        Log.i(getClass().getSimpleName(), "Problemas ? "+problemas);

        return resultado;


    }
}