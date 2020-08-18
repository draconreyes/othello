package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.othello.R;
import com.example.othello.modelo.Partidad;

public class Juego extends AppCompatActivity {
    private Partidad partidad;
    private int turno;
    private String id_partidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("--------SE LANZO ONCRETE-------------------------------------------------------");
        id_partidad=getIntent().getStringExtra("id_partidad");
        turno=getIntent().getIntExtra("turno",0);
        partidad= new Partidad(this,getBaseContext(),turno,id_partidad);
    }
}