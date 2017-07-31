package com.dev.cyka.cykavodka;

import android.app.Application;

import io.smooch.core.Smooch;


public class SmoochInit extends Application{

    @Override
    public void onCreate(){
        super.onCreate();

        Smooch.init(this, "b4zzpyiunli1s7k3f83uw358k"); //initialise le service de messages via smooch (à partir de la clé d'identification du projet)
    }
}
