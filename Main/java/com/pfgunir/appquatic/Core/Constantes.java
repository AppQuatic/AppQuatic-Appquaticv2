package com.pfgunir.appquatic.Core;


import com.google.android.gms.maps.model.LatLng;

public class Constantes {


    public final static String DB_NAME = "AppquaticDB.db";
    public final static String DB_PATH = "/data/data/com.pfgunir.appquatic2/databases/";

    public final static String NombreFicheroPreferencias = "AppQuatic_preferences";


    public final static String KEYOPENWEATHER = "beb9ec6812fa8602befaa7a5131bddc8" ;
    public final static String KEYAMET = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZmN1bmlyQGdtYWlsLmNvbSIsImp0aSI6IjRhZWJiNmMyLWM1M2MtNGVkNS1iMmUwLWMxNzJjY2MxMmE0MCIsImlzcyI6IkFFTUVUIiwiaWF0IjoxNjAxOTg2OTUwLCJ1c2VySWQiOiI0YWViYjZjMi1jNTNjLTRlZDUtYjJlMC1jMTcyY2NjMTJhNDAiLCJyb2xlIjoiIn0.Lu6TMl5cnqKJ0ruih4iSTul65a_34la2Aaoxd4mplbA";
    public final static String KEYGOOGLEMAPS = "beb9ec6812fa8602befaa7a5131bddc8" ;


    /** Constante posición  UNIR. */
    public static final LatLng PosicionUnir = new LatLng(42.462303617625736, -2.424156273091362);
    public static String Debil="débil";
    public static String Moderado="moderado";
    public static String Fuerte="fuerte";


    public static Double Viento_mini_Windsurf=2.0;
    public static Double Viento_mini_Kitesurf=2.0;
    public static Double Viento_mini_Surf=0.0;

    public static Double Viento_max_Windsurf=8.0;
    public static Double Viento_max_Kitesurf=6.0;
    public static Double Viento_max_Surf=4.0;


    public static Double Masa_Corporal_baja = 18.5;
    public static Double Masa_Corporal_normal = 24.9;
    public static Double Masa_Corporal_alta = 25.0;
    public static Double Masa_Corporal_muy_alta = 30.0;
}
