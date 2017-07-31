package com.dev.cyka.cykavodka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class permis extends AppCompatActivity {

    public float taux;
    public boolean prob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permis1);



        Bundle extras = getIntent().getExtras();
        if (extras != null){
            taux = extras.getFloat("Taux");
            prob = extras.getBoolean("Prob");
        }
        if (!prob){
            setContentView(R.layout.activity_permis2);
            TextView infsup = (TextView)findViewById(R.id.supinfTxt);
            if (taux <=0.2f){
                infsup.setText("inférieure");
                findViewById(R.id.driveok).setVisibility(View.VISIBLE);
            } else {
                infsup.setText("supérieure");
                findViewById(R.id.driveno).setVisibility(View.VISIBLE);
            }
        } else {
            TextView infsup = (TextView)findViewById(R.id.supinfTxt);
            if (taux <=0.5f){
                infsup.setText("inférieure");
                findViewById(R.id.driveok).setVisibility(View.VISIBLE);
            } else {
                infsup.setText("supérieure");
                findViewById(R.id.driveno).setVisibility(View.VISIBLE);
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
}
