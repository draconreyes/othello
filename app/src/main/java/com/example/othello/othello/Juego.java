package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.othello.R;
import com.example.othello.db.Database;
import com.example.othello.modelo.Partidad;
import com.google.firebase.database.Query;

public class Juego extends AppCompatActivity {
    private Partidad partidad;
    private int turno;
    private String id_partidad;
    private Database db;
    private Query q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db= new Database();
        setContentView(R.layout.activity_main);
        System.out.println("--------SE LANZO ONCRETE-------------------------------------------------------");
        id_partidad=getIntent().getStringExtra("id_partidad");
        turno=getIntent().getIntExtra("turno",0);
        partidad= new Partidad(this,getBaseContext(),turno,id_partidad);
    }
    @Override
   protected void onDestroy() {
        super.onDestroy();
        db.Partidad_terminada(id_partidad);
   }
}