package com.dev.cyka.cykavodka;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
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
    float coeff;
    String coeffi;
    public String onRadioButtonClicked(View View) {
         final EditText weight = (EditText) findViewById(R.id.weight);
         final TextView poids = (TextView) findViewById(R.id.poids);
         final Button valider2 = (Button) findViewById(R.id.valider2);
        TextView textView = (TextView) findViewById(R.id.textView);
        boolean checked = ((RadioButton) View).isChecked();
        switch (View.getId()) {
            case R.id.radioHomme:
                if (checked)
                    Toast.makeText(this, "Homme", Toast.LENGTH_SHORT).show();
                coeff = 0.7f;
                coeffi=String.valueOf(coeff);
                poids.setVisibility(View.VISIBLE);
                weight.setVisibility(View.VISIBLE);
                valider2.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                break;
            case R.id.radioFemme:
                if (checked)
                    Toast.makeText(this, "Femme", Toast.LENGTH_SHORT).show();
                coeff = 0.6f;
                coeffi=String.valueOf(coeff);
                poids.setVisibility(View.VISIBLE);
                weight.setVisibility(View.VISIBLE);
                valider2.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                break;
        }
        return new String(coeffi);
    }
    public String saisiePoids(View View){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        final TextView verresBus = (TextView) findViewById(R.id.verresBus);
        final EditText nbreVerresBus = (EditText) findViewById(R.id.nbreVerresBus);
        final Button valider3 = (Button) findViewById(R.id.valider3);
        final EditText weight = (EditText) findViewById(R.id.weight);
        verresBus.setVisibility(View.VISIBLE);
        nbreVerresBus.setVisibility(View.VISIBLE);
        valider3.setVisibility(View.VISIBLE);
        Poids = Float.parseFloat(weight.getText().toString());
        String stringPoids = weight.getText().toString();
        return (stringPoids);
    }
    public float Poids;
    public void saisieVerres (View View){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        EditText nbreVerresBus = (EditText) findViewById(R.id.nbreVerresBus);
        int  nombreVerres = Integer.parseInt(nbreVerresBus.getText().toString()) ;
        Intent intent = new Intent(this,CalcResults.class);
        intent.putExtra("VERRES",nombreVerres);//VALEUR A PARSER
        //intent.putExtra("POIDS",saisiePoids(View)); //VALEUR A PARSER
        intent.putExtra("POIDS", Poids);
        //String coefficient = new String(coeffi);
        intent.putExtra("COEFFICIENT",coeff);//VALEUR A PARSER
        startActivity(intent);
    }
}
