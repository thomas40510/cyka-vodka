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


public class results extends AppCompatActivity {

    public double Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Time = getIntent().getDoubleExtra("data", -1); // récupère la valeur du temps moyen depuis l'activité précédente
        TextView txtTime = (TextView) findViewById(R.id.txtTime);
        txtTime.setText(""+Time);

        if (Time <= 1){ // temps de réaction normal
            TextView sec = (TextView) findViewById(R.id.sec);
            sec.setText(" seconde");
        }
        if (Time > 1.8){ // temps de réaction altéré (le temps correspondant à quelqu'un à la dose limite est de 1.5s, on laisse ici une marge d'erreur de 0.3s)
            findViewById(R.id.msg1).setVisibility(View.VISIBLE);
            findViewById(R.id.msg2).setVisibility(View.VISIBLE);
            findViewById(R.id.msg3).setVisibility(View.VISIBLE);
        }
        if (Time > 3){ // temps de réaction trèe élevé (débloque un achievement)
            SharedPreferences prefs = getSharedPreferences(AchievePrefs.ACH_PREFS, MODE_PRIVATE);
            AchievePrefs.Achieve[1] = prefs.getString("achieve1", "");
            if (!AchievePrefs.Achieve[1].equals("1")){
                SharedPreferences.Editor editor = getSharedPreferences(AchievePrefs.ACH_PREFS, MODE_PRIVATE).edit();
                editor.putString("achieve1", "1");
                editor.commit();
                Toast.makeText(this, "Achievement unlocked !", Toast.LENGTH_SHORT).show();
            }
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
    public void close (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
