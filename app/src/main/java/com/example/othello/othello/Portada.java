package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.othello.R;
import com.example.othello.othello.Juego;

public class Portada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);
    }

    public void juego(View view){
        Intent juego = new Intent(this, Juego.class);
        startActivity(juego);
    }
}