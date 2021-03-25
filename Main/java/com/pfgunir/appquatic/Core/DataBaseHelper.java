package com.pfgunir.appquatic.Core;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DB_VER=1;
    SQLiteDatabase db;
    private final Context mContext;

    public DataBaseHelper(Context context) {
        super(context, Constantes.DB_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDB() {
        boolean dbExist = checkDB();
        if (dbExist) {

        } else {
            this.getWritableDatabase();

            try {
                copyDB();
            } catch (Exception e) {
                throw new Error("Error copying DB");

            }

        }
    }
    private boolean checkDB() {
        SQLiteDatabase check = null;
        try {
            String dbPath = Constantes.DB_PATH + Constantes.DB_NAME;
            check = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (check != null) {
            check.close();
        }

        return check != null ? true : false;
    }
    private void copyDB() throws IOException {
        InputStream dbInput = mContext.getAssets().open("databases/" + Constantes.DB_NAME);
        String outFile = Constantes.DB_PATH + Constantes.DB_NAME;
        OutputStream dbOutput = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = dbInput.read(buffer)) > 0) {
            dbOutput.write(buffer, 0, length);
        }

        dbOutput.flush();
        dbOutput.close();
        dbInput.close();

    }


    public void openDB() {
        String dbPath = Constantes.DB_PATH + Constantes.DB_NAME;
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public Usuario canloggin(String user, String contra) {

        String query = "select * from users where nombre='" +
                user + "' and pass='" + contra + "'";



        this.openDB();
        Cursor fila = db.rawQuery(query, null);

        try {
            /*Condicional if preguntamos si cursor tiene algun dato*/
            if (fila.moveToFirst()) {
//capturamos los valores del cursos y lo almacenamos en variable
                String usua = fila.getString(fila.getColumnIndex("nombre"));
                String pass = fila.getString(fila.getColumnIndex("pass"));

                //preguntamos si los datos ingresados son iguales
                if (user.equals(usua) && contra.equals(pass)) {

                    //si son iguales entonces vamos a otra ventana
                    Usuario usuario = new Usuario(fila.getString(fila.getColumnIndex("user_id")));
                    usuario.setUsernombre(fila.getString(fila.getColumnIndex("nombre")));
                    usuario.setPass(fila.getString(fila.getColumnIndex("pass")));
                    usuario.setAltura(fila.getString(fila.getColumnIndex("altura")));
                    usuario.setPeso(fila.getString(fila.getColumnIndex("peso")));
                    usuario.setModofavoritos(Integer.valueOf(fila.getString(fila.getColumnIndex("modofavoritos"))));
                    usuario.setAvatar(fila.getString(fila.getColumnIndex("avatar")));


                    return usuario;
                }
            }//si la primera condicion no cumple entonces que envie un mensaje toast
            else {
                // Toast toast = Toast.makeText(this.mContext, "Datos incorrectos", Toast.LENGTH_SHORT);
                //toast.show();
                return null;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return null;
    }

    public Usuario getUsuario(String usuario_id){

        //String query = "select user_id,nombre,pass,altura,peso,modofavoritos,avatar from users where user_id='" +
                //usuario_id  + "'";
        String query = "select * from users where user_id='" +
                usuario_id  + "'";

        Log.i(getClass().getSimpleName(), "Querry -- a " + query);



this.openDB();
        Cursor fila = db.rawQuery(query, null);
        this.close();
        try {
            /*Condicional if preguntamos si cursor tiene algun dato*/
            if (fila.moveToFirst()) {
//capturamos los valores del cursos y lo almacenamos en variable
                String usua = fila.getString(fila.getColumnIndex("nombre"));
                String pass = fila.getString(fila.getColumnIndex("pass"));

                //preguntamos si los datos ingresados son iguales


                    //si son iguales entonces vamos a otra ventana
                    Usuario usuario = new Usuario(fila.getString(fila.getColumnIndex("user_id")));
                    usuario.setUsernombre(fila.getString(fila.getColumnIndex("nombre")));
                    usuario.setPass(fila.getString(fila.getColumnIndex("pass")));
                    usuario.setAltura(fila.getString(fila.getColumnIndex("altura")));
                    usuario.setPeso(fila.getString(fila.getColumnIndex("peso")));
                    usuario.setModofavoritos(Integer.valueOf(fila.getString(fila.getColumnIndex("modofavoritos"))));
                    usuario.setAvatar(fila.getString(fila.getColumnIndex("avatar")));



                    return usuario;

            }
            //si la primera condicion no cumple entonces que envie un mensaje toast

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return null;

    }

    public ArrayList<Spot> getSpots() {
        ArrayList<Spot> listaSpots = new ArrayList<Spot>();


        String selectQuery = "SELECT  * FROM " + "spots";
this.openDB();
        Cursor cursor = db.rawQuery(selectQuery, null);
this.close();

        if (cursor.moveToFirst()) {
            do {
                // Log.i(getClass().getSimpleName(), cursor.getString(cursor.getColumnIndex("Nombre"))+"  "+ cursor.getString(cursor.getColumnIndex("Idspot")));
                Spot spot = new Spot(cursor.getString(cursor.getColumnIndex("spot_id")));
                //spot.setIdspot(cursor.getString(cursor.getColumnIndex("Idspot")));
                spot.setNombreSpot(cursor.getString(cursor.getColumnIndex("nombre_spot")));
                spot.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
                spot.setLon(cursor.getDouble(cursor.getColumnIndex("lon")));
                // Log.i(getClass().getSimpleName(), String.valueOf(spot.getLon()));
                spot.setIdPlayaAemet(cursor.getString(cursor.getColumnIndex("playa_Aemet_id")));
                listaSpots.add(spot);

            } while (cursor.moveToNext());
            return listaSpots;

        }






        return null;
    }



    public int upDatePreferencias(ContentValues cv, String userid) {
        this.openDB();
        String[] args = new String []{userid};
        int resultado = db.update("users",cv,"user_id=?", args);
        this.close();
        return resultado;
    }

    public ArrayList<String> getFavoritos (String user_id) {

        ArrayList<String> lista_id_favoritos = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM "+"favoritos" + " where user_id= '" + user_id+"'";
        Log.e(getClass().getSimpleName(), selectQuery);
        this.openDB();
        Cursor cursor = db.rawQuery(selectQuery, null);
        this.close();

        if (cursor.moveToFirst()) {
            do {

                lista_id_favoritos.add(cursor.getString(cursor.getColumnIndex("spot_id")));

            } while (cursor.moveToNext());
            return lista_id_favoritos;

        }
        else{
            return null;
        }

    }
    public int addFavorito (ContentValues cv) {

        String user_id = cv.getAsString("user_id");
        String spot_id = cv.getAsString("spot_id");
        String selectQuery = "SELECT  * FROM "+"favoritos" + " where user_id= '" + user_id+"' AND spot_id= '"+ spot_id+"'";
        String[] args = new String []{user_id , spot_id};
        Integer resultado=null;

        this.openDB();
        Cursor cursor = db.rawQuery(selectQuery, null);
        this.close();
        if (cursor.moveToFirst()) {
            do {

                resultado =db.delete("favoritos"," user_id= ? AND spot_id= ? ",args);
               // Log.e(getClass().getSimpleName(), "resultado --- agregar Spot " + resultado );

            } while (cursor.moveToNext());
            return resultado;

        }
        else{
            resultado = Integer.valueOf((int) db.insert("favoritos",null,cv));
            //Log.e(getClass().getSimpleName(), "resultado --- agregar Spot " + resultado );
            return resultado;
        }





    }

    public int isSpotFavorito(String spot_id){
        String selectQuery = "SELECT  * FROM "+"favoritos" + " where spot_id= '"+ spot_id+"'";
        this.openDB();
        Cursor cursor = db.rawQuery(selectQuery, null);
        this.close();
        if (cursor.moveToFirst()) {

        return  1;
        }
        else{
            return 0;
        }


    }

    //Log.i(getClass().getSimpleName(), usuario.getUsernombre());
}
