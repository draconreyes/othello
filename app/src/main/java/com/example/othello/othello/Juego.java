package com.example.othello.othello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.othello.R;
import com.example.othello.db.Database;
import com.example.othello.modelo.Cordenada;
import com.example.othello.modelo.Partidad;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Juego extends AppCompatActivity implements Observer {
    private Partidad partidad;
    private int turno;
    private String id_jugador;
    private String id_partidad;
    private Database db;
    private Query q;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        db= new Database();
        setContentView(R.layout.activity_main);
        System.out.println("--------SE LANZO ONCRETE-------------------------------------------------------");
        id_partidad=getIntent().getStringExtra("id_partidad");
        id_jugador=getIntent().getStringExtra("id_jugador");
        turno=getIntent().getIntExtra("turno",0);
        partidad= new Partidad(this,getApplicationContext(),turno,id_partidad);
    }
    @Override
   protected void onDestroy() {
        super.onDestroy();
        db.Partidad_terminada(id_partidad);
   }

    @Override
    public void update(Observable observable, Object o) {
        ArrayList<Object> args = (ArrayList<Object>)  o;
        String mensaje=(String) args.get(0);
        Intent intent = new Intent(this, Mensaje.class);
        intent.putExtra("mensaje", mensaje);
        intent.putExtra("id_jugador", id_jugador);
        startActivity(intent);
    }
}