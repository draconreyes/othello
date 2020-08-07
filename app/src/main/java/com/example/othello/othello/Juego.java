package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.othello.R;
import com.example.othello.modelo.Partidad;

public class Juego extends AppCompatActivity {
    private Partidad partidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        partidad= new Partidad(this,getBaseContext());
    }
}