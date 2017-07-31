package com.dev.cyka.cykavodka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.smooch.ui.ConversationActivity;

public class credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }
    public void goBack (View view){
        finish();
    }
    public void contactDevs (View view){
        ConversationActivity.show(this);
    }
}
