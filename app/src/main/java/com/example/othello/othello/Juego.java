package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import androidx.gridlayout.widget.GridLayout;
import com.example.othello.R;
import com.example.othello.modelo.Tablero;

public class Juego extends AppCompatActivity {
    private Partidad partidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        partidad= new Partidad(this,getBaseContext());
    }
}