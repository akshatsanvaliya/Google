package com.example.google;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        new Handler().postDelayed( () -> {
            Intent i = new Intent(splash.this, MainActivity.class);
            startActivity( i );
        },4000 );
    }
}