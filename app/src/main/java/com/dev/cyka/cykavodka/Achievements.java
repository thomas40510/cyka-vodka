package com.dev.cyka.cykavodka;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.Toast;

public class Achievements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int i = 0;

        SharedPreferences prefs = getSharedPreferences(AchievePrefs.ACH_PREFS, MODE_PRIVATE); //accède au fichier de sauvegarde local des achievements
        //utilise l'array "AchievePrefs.Achieve[] comme iuntermédiaire entre les SharedPreferences et l'array final "achieves"
        AchievePrefs.Achieve[1] = prefs.getString("achieve1", "");
        AchievePrefs.Achieve[2] = prefs.getString("achieve2", "");
        AchievePrefs.Achieve[3] = prefs.getString("achieve3", "");

        String[] achieves = {"0", AchievePrefs.Achieve[1], AchievePrefs.Achieve[2], AchievePrefs.Achieve[3]};

        while ( i!=4){ //boucle de vérification de l'état des achievements et déblocage des icônes

            if (achieves[i].equals("1")){
                String achieveID = "achieve"+i;
                int resID = getResources().getIdentifier(achieveID, "id", getPackageName()); //convertit le string "achieveID" en nom de ressource pour faire le lien avel le xml
                TableRow achieverow = ((TableRow) findViewById(resID));
                achieverow.setAlpha(1); // change l'opacité de l'achievement correspondant (icône + texte)
            }
            i = i+1;
        }
    }

    /**
     * Actions for toolbar menu
     */
    @Override
    //load menu file//
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_achievements, menu); //your file name
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    //set on-click actions//
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset_progress:
                //partie de code permettant d'effacer la progression (affiche un popup qui demande confirmation à l'utilisateur)
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation")
                        .setMessage("Êtes-vous sûr de vouloir effacer votre progression ?")
                        .setPositiveButton("Oui !", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //si l'utilisateur confirme, efface la progression et affecte à tous les clés du fichier local la valeur "0" (non débloqué)
                                SharedPreferences.Editor editor = getSharedPreferences(AchievePrefs.ACH_PREFS, MODE_PRIVATE).edit();
                                editor.putString("achieve1", "0")
                                        .putString("achieve2", "0")
                                        .putString("achieve3", "0");
                                        editor.commit();

                                Toast.makeText(getApplicationContext(), "Progression effacée !", Toast.LENGTH_SHORT).show();

                                //diminue l'opacité des achievements (icône + texte)
                                findViewById(R.id.achieve1).setAlpha(0.4f);
                                findViewById(R.id.achieve2).setAlpha(0.4f);
                                findViewById(R.id.achieve3).setAlpha(0.4f);
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void close (View view){
        finish();
    }
}
