package com.dev.cyka.cykavodka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;


public class Reflex extends AppCompatActivity {
    public int n, r;
    public long begin, end;
    public ImageView square;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflex);


        Random rand = new Random();
        int n = rand.nextInt(100); // génère une valeur aléatoire dans [0;100[
        if (n == 42){
            findViewById(R.id.cocktail).setVisibility(View.VISIBLE); // affiche le cocktail (easter egg) avec une chance sur 100
        }
    }

    /**
     * Actions for toolbar menu
     */
    @Override
    //load menu file//
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //set on-click actions//
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.open_credits:
                Intent credits = new Intent(this, credits.class);
                startActivity(credits);
                return true;
            case R.id.open_achievements:
                Intent achieve = new Intent(this, Achievements.class);
                startActivity(achieve);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void show3 (View view) {
        n = 1; //initialise les variables
        r = 0;
        begin = System.currentTimeMillis(); //récupère l'heure du système
        show4(view);
    }

    public void show4(View view) {
        Random rand = new Random();
        findViewById(R.id.button2).setVisibility(View.INVISIBLE);
        int i = rand.nextInt(7); //génère un entier aléatoire sur [0;7[
        if (i == 0 || i == r) { //effectue le test si le nombre i est égal à 0 ou r (r est la valeur précédente de i). Ainsi, l'application n'affiche jamais 2 fois le même carré
            show4(view);        //si i est égal à 0 ou r, relance la méthode
        }
        else {
            r = i;
            String squareID = "square" + i; //crée un String intermédiaire, à partir du i choisi (si i=1, le String prendra la valeur "square1")
            int resID = getResources().getIdentifier(squareID, "id", getPackageName()); //convertit le String "squareId" en nom de ressource (pour l'appel au xml)
            square = ((ImageView) findViewById(resID));
            square.setVisibility(View.VISIBLE);
            square.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (n == 5) {
                        findViewById(R.id.button2).setVisibility(View.VISIBLE);
                        square.setVisibility(View.INVISIBLE);
                        end = System.currentTimeMillis(); // récupère le temps du système, en millisecondes

                        double finalTime = (double)((end-begin)/5)/1000; // calcule le temps moyen, à partir du temps initial du système, et du temps final. Divise par 5 pour avoir la moyenne pour 1 carré
                        Intent intent = new Intent(getApplicationContext(), results.class); // lance l'activité "results.class"
                        intent.putExtra("data", finalTime); //envoie la valeur du temps final à l'activité "results.class"
                        startActivity(intent);
                    } else {
                        n = n + 1;
                        square.setVisibility(View.INVISIBLE);
                        show4(view);
                    }
                }
            });

        }
    }

    public void cocktailUnlock (View view){ // méthode pour débloquer l'achievement du cocktail (qui apparaît avec une chance sur 100)
        SharedPreferences prefs = getSharedPreferences(AchievePrefs.ACH_PREFS, MODE_PRIVATE); //accède au fichier de sauvegarde local
        AchievePrefs.Achieve[3] = prefs.getString("achieve3", ""); // récupère la valeur correspondant à l'état de l'achievement ("1" = débloqué / "0" = non débloqué)
        if (!AchievePrefs.Achieve[3].equals("1")) { // vérifie si l'achievement est débloqué
            SharedPreferences.Editor editor = getSharedPreferences(AchievePrefs.ACH_PREFS, MODE_PRIVATE).edit(); // édite le fichier local
            editor.putString("achieve3", "1"); // affecte à la clé "achieve3" la valeur "1" dans le fichier de sauvegarde
            editor.commit();

            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); // accède au vibrateur
            v.vibrate(300); // fait vibrer le téléphone pendant 300 millisecondes

            Toast.makeText(this, "Achievement Unlocked !", Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.cocktail).setVisibility(View.INVISIBLE);
    }
}
