package com.dev.cyka.cykavodka;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CalcResults extends AppCompatActivity {

    //String Search, Search2, Search3;
    int nbreVerres;
    float poids, coefficient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_results);
        TextView afficher = (TextView) findViewById(R.id.afficher);
        TextView afficher2 = (TextView) findViewById(R.id.afficher2);
        TextView afficher3 = (TextView) findViewById(R.id.afficher3);
        TextView afficherTaux = (TextView) findViewById(R.id.taux);
        TextView permis = (TextView) findViewById(R.id.permis);
        String sexe;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            nbreVerres = extras.getInt("VERRES");
            poids = extras.getFloat("POIDS");
            coefficient = extras.getFloat("COEFFICIENT");
        }
        /**int nbreVerres = Integer.parseInt(Search);
        float poids = Float.parseFloat(Search2);
        float coefficient = Float.parseFloat(Search3);
         */
        if (coefficient==0.7f){
            sexe="un Homme";
        }
        else{
            sexe="une Femme";
        }
        taux = (nbreVerres*10)/(poids*coefficient);
        afficher.setText("Vous avez bu " +nbreVerres+" verres.");
        afficher2.setText("Vous pesez "+poids+" kilos.");
        afficher3.setText("Vous êtes "+sexe+".");
        afficherTaux.setText("Votre taux d'alcoolémie estimé est de "+taux+" g/L de sang");
        afficher.setVisibility(View.VISIBLE);
        afficher2.setVisibility(View.VISIBLE);
        afficher3.setVisibility(View.VISIBLE);
        afficherTaux.setVisibility(View.VISIBLE);
        permis.setVisibility(View.VISIBLE);

        if (taux > 10){
            SharedPreferences pref = getSharedPreferences(AchievePrefs.ACH_PREFS, MODE_PRIVATE);
            AchievePrefs.Achieve[2] = pref.getString("achieve2", "");
            if (!AchievePrefs.Achieve[2].equals("1")){
                SharedPreferences.Editor editor = getSharedPreferences(AchievePrefs.ACH_PREFS, MODE_PRIVATE).edit();
                editor.putString("achieve2", "1");
                editor.commit();

                Toast.makeText(this, "Achievement unlocked !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public float taux;

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

     boolean prob;

     public void onClickOui (View view){
         prob = true;
         gotoNext();
     }
     public void onClickNon (View view){
         prob = false;
         gotoNext();
     }
     public void gotoNext(){
         Intent intent = new Intent(this, permis.class);
         intent.putExtra("Taux", taux).putExtra("Prob", prob);
         startActivity(intent);
     }

}
