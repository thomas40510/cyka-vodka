package com.dev.cyka.cykavodka;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.w3c.dom.Text;

import io.smooch.core.Smooch;

public class MainActivity extends AppCompatActivity {

    //Remote Config keys
    private static final String LOADING_PHRASE_CONFIG_KEY = "loading_phrase";
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";
    private static final String UPDATE_MESSAGE = "update_message";
    private static final String UPDATE_LINK = "update_link";

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private TextView mWelcomeTextView;
    private TextView mUpdateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView versionname = (TextView) findViewById(R.id.versionname);
        String version = BuildConfig.VERSION_NAME; //récupère le nom de la version dans "build.gradle"
        versionname.setText(version); //écrit le nom de la version sur l'écran d'accueil
        fetchMsgCount(); // exécute la méthode récupérant le nombre de messages non lus

        //si l'utilisateur clique sur "messages non lus", lance la méthode "fetchMsgCount"
        TextView unreadTxt = (TextView) findViewById(R.id.msgFetch);
        unreadTxt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fetchMsgCount();
            }
        });

        mWelcomeTextView = (TextView) findViewById(R.id.welcomeMsg);
        mUpdateTextView = (TextView) findViewById(R.id.update);
        TextView fetchButton = (TextView) findViewById(R.id.txtfetch);
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchWelcome();
            }
        });

        /**
         * [Firebase "Remote config" Code]
         */

        //get Remote Config instance
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Create Remote Config Setting to enable developer mode.
        // Fetching configs from the server is normally limited to 5 requests per hour.
        // Enabling developer mode allows many more requests to be made per hour, so developers
        // can test different config values during development.
        // [START enable_dev_mode]
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        // [END enable_dev_mode]

        //set RemoteConfig defaults (if fetch fails)
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        fetchWelcome();
    }

    /**
     * Fetch welcome message from server.
     */
    private void fetchWelcome() {
        mWelcomeTextView.setText(mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY)); //sets loading message

        long cacheExpiration = 1800; // expiration du cache (1/2 heure en secondes).
        // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
        // the server.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating that any previously
        // fetched and cached config would be considered expired because it would have been fetched
        // more than cacheExpiration seconds ago. Thus the next fetch would go to the server unless
        // throttling is in progress. The default expiration duration is 43200 (12 hours).
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "MotD récupéré !",
                                    Toast.LENGTH_SHORT).show();

                            // Once the config is successfully fetched it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(MainActivity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        displayWelcomeMessage();
                    }
                });
        // [END fetch_config_with_callback]
    }

    /**
     * display welcome message as fetched from welcome_message.
     */
    // [START display_welcome_message]
    private void displayWelcomeMessage() {
        // [START get_config_values]
        String welcome_message = mFirebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY);
        String updatemsg = mFirebaseRemoteConfig.getString(UPDATE_MESSAGE);
        String updatelink = mFirebaseRemoteConfig.getString(UPDATE_LINK);
        // [END get_config_values]
        mWelcomeTextView.setText(welcome_message);
        mUpdateTextView.setText(updatemsg);
        final Uri updateuri = Uri.parse(updatelink);

        mUpdateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent openUpdate = new Intent(Intent.ACTION_VIEW, updateuri);
                startActivity(openUpdate);
            }

        });

    }
    // [END display_welcome_message]

    /**
     * [End of Firebase "RC" code]
     */

    /**
     * Actions for toolbar menu
     */
    @Override
    //load menu file
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu); //menu file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //set on-click actions
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

    public void fetchMsgCount (){
        int n = Smooch.getConversation().getUnreadCount(); //récupère le nombre de messages non lus
        TextView unreadNbr = (TextView) findViewById(R.id.msgCount);
        unreadNbr.setText(""+n);
        if (n<=1){
            TextView unreadTxt = (TextView) findViewById(R.id.msgFetch);
            unreadTxt.setText(" Message non lu");
        }
    }
    public void gotoReflex (View view){
        Intent intent = new Intent(this, Reflex.class);
        startActivity(intent);
    }
    public void gotoCalculate (View view){
        Intent intent = new Intent(this, Calculator.class);
        startActivity(intent);
    }
}
